package com.asad.jobsportal.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.asad.jobsportal.data.network.ApiService
import com.asad.jobsportal.data.network.response.ResponseJobsItem

class JobsRepository(private val apiService: ApiService) {

    fun getJobs() : LiveData<PagingData<ResponseJobsItem>>{
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                JobsPagingSource(apiService)
            }
        ).liveData
    }

    fun searchJobs(description: String, location: String, isFulltime: Boolean) : LiveData<PagingData<ResponseJobsItem>>{
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchJobsPagingSource(apiService, description, location, isFulltime)
            }
        ).liveData
    }

    fun getDetailJob(id : String) = apiService.detailJob(id)

}