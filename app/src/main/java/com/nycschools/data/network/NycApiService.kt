package com.nycschools.data.network

import com.nycschools.data.model.NycSchool
import retrofit2.Response
import retrofit2.http.GET

interface NycApiService {

    @GET("resource/s3k6-pzi2.json")
    suspend fun getListOfSchools(): Response<List<NycSchool>>

    @GET("resource/f9bf-2cp4.json")
    suspend fun getScoresForAllSchools() : Response<List<NycSchool>>
}