package com.asad.jobsportal.data

import com.asad.jobsportal.data.network.ApiConfig

object Injection {

    fun provideRepository() : JobsRepository{
        val apiService = ApiConfig.getApiService()
        return JobsRepository(apiService)
    }
}