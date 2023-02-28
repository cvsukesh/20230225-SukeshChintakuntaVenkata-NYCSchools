package com.nycschools.mvvm.repo

import android.content.Context
import com.nycschools.data.network.BaseDataSource
import com.nycschools.data.network.NYCResponse
import com.nycschools.data.network.NycApiService
import com.nycschools.data.model.NycSchool
import com.nycschools.room.NycSchoolDAO
import javax.inject.Inject

/**
 * Repository class which handles all the business logic & all the DB Operations.
 */
class NycRepositoryImpl @Inject constructor(
    override var context: Context,
    val nycApiService: NycApiService,
    val nycSchoolDAO: NycSchoolDAO
) : BaseDataSource(context),
    NycRepository {
    override suspend fun getNewyorkSchools(): NYCResponse<List<NycSchool>> {
        return getResult {
            nycApiService.getListOfSchools()
        }
    }

    override suspend fun getSatScoresForSchools(): NYCResponse<List<NycSchool>> {
        return getResult {
            nycApiService.getScoresForAllSchools()
        }
    }

    override suspend fun insertNycHighSchoolRecords(listOfRecords: List<NycSchool>?) {
        clearAllTheRecords()
        listOfRecords?.forEach { nycSchool ->
            nycSchoolDAO.insertSchoolInDb(nycSchool)
        }
    }

    override suspend fun updateNycSchoolRecords(listOfRecords: List<NycSchool>?) {
        listOfRecords?.forEach { nycSchool ->
            nycSchool.apply {
                nycSchoolDAO.updateSatScoresInDb(
                    dbn,
                    satTestTakers,
                    satCriticalReadingAvgScore,
                    satMathAvgTakers,
                    satWritingAvgScore
                )
            }
        }
    }

    override suspend fun getAllRecords(): List<NycSchool> = nycSchoolDAO.getSchoolsList()

    override suspend fun getNycSchoolRecord(id: String): NycSchool =
        nycSchoolDAO.getRecordFromId(id)

    override suspend fun clearAllTheRecords() = nycSchoolDAO.dropAllTheRecords()
}