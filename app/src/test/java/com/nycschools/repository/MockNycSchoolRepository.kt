package com.nycschools.repository

import android.content.Context
import com.nycschools.data.model.NycSchool
import com.nycschools.data.network.NYCResponse
import com.nycschools.data.network.NycApiService
import com.nycschools.mvvm.repo.NycRepository
import com.nycschools.room.NycSchoolDAO
import com.nycschools.utils.AppTestUtils

class MockNycSchoolRepository(
    var context: Context,
    var nycSchoolApi: NycApiService,
    var nycSchoolDao: NycSchoolDAO
) : NycRepository {

    var isSuccess = true
    override suspend fun getNewyorkSchools(): NYCResponse<List<NycSchool>> {
        val list: ArrayList<NycSchool> =
            AppTestUtils.readJsonResponseFileForList("appConfig/nyc_high_school_list.json")
        return NYCResponse.success(list)
    }

    override suspend fun insertNycHighSchoolRecords(listOfRecords: List<NycSchool>?) {
        listOfRecords.let {
            it?.forEach { nycSchool ->
                nycSchoolDao.insertSchoolInDb(nycSchool)
            }
        }
    }

    override suspend fun updateNycSchoolRecords(listOfRecords: List<NycSchool>?) {
        TODO("Not yet implemented")
    }

    override suspend fun getSatScoresForSchools(): NYCResponse<List<NycSchool>> {
        val list: ArrayList<NycSchool> =
            AppTestUtils.readJsonResponseFileForList("appConfig/nyc_school_list_with_sat_scores.json")
        return NYCResponse.success(list)
    }

    override suspend fun getAllRecords(): List<NycSchool> {
        TODO("Not yet implemented")
    }

    override suspend fun getNycSchoolRecord(id: String): NycSchool {
        TODO("Not yet implemented")
    }

    override suspend fun clearAllTheRecords() {
        TODO("Not yet implemented")
    }
}