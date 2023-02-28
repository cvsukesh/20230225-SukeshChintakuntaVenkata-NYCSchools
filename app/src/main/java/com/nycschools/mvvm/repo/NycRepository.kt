package com.nycschools.mvvm.repo

import com.nycschools.data.network.NYCResponse
import com.nycschools.data.model.NycSchool

interface NycRepository {
    suspend fun getNewyorkSchools() : NYCResponse<List<NycSchool>>

    suspend fun insertNycHighSchoolRecords(listOfRecords: List<NycSchool>?)

    suspend fun updateNycSchoolRecords(listOfRecords: List<NycSchool>?)

    suspend fun getSatScoresForSchools() : NYCResponse<List<NycSchool>>

    suspend fun getAllRecords(): List<NycSchool>

    suspend fun getNycSchoolRecord(id: String): NycSchool

    suspend fun clearAllTheRecords()
}