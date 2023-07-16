package com.asad.jobsportal.data.network

import com.asad.jobsportal.data.network.response.ResponseJobs
import com.asad.jobsportal.data.network.response.ResponseJobsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recruitment/positions.json")
    suspend fun searchJobs(@Query("page") page : Int,
                           @Query("description") description : String,
                           @Query("location") location : String,
                           @Query("full_time") isFullTime : Boolean) : List<ResponseJobsItem>

    @GET("recruitment/positions.json")
    suspend fun getJobs(@Query("page") page : Int) : List<ResponseJobsItem>

    @GET("recruitment/positions/{id}")
    fun detailJob(@Path("id") id : String): Call<ResponseJobsItem>
}