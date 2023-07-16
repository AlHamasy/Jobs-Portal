package com.asad.jobsportal.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asad.jobsportal.data.JobsRepository
import com.asad.jobsportal.data.network.response.ResponseJobsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailJobViewModel(private val jobsRepository: JobsRepository) : ViewModel() {

    val resultDetailJob = MutableLiveData<ResponseJobsItem>()
    val errorDetailJob = MutableLiveData<String>()

    fun getDetailJob(id : String){
        jobsRepository.getDetailJob(id).enqueue(object : Callback<ResponseJobsItem>{
            override fun onResponse(call: Call<ResponseJobsItem>, response: Response<ResponseJobsItem>) {
                if (response.isSuccessful){
                    resultDetailJob.postValue(response.body())
                }
                else{
                    errorDetailJob.postValue("Something wrong")
                }
            }
            override fun onFailure(call: Call<ResponseJobsItem>, t: Throwable) {
                errorDetailJob.postValue(t.message)
            }
        })
    }
}