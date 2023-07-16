package com.asad.jobsportal.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.asad.jobsportal.data.JobsRepository
import com.asad.jobsportal.data.network.response.ResponseJobsItem

class HomeViewModel(private val jobsRepository: JobsRepository) : ViewModel() {

    val getJobs : LiveData<PagingData<ResponseJobsItem>> =
        jobsRepository.getJobs().cachedIn(viewModelScope)

    fun searchJobs(description: String = "", location: String = "", isFulltime: Boolean = false) : LiveData<PagingData<ResponseJobsItem>> {
        return jobsRepository.searchJobs(description, location, isFulltime).cachedIn(viewModelScope)
    }
}