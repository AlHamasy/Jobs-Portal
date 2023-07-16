package com.asad.jobsportal.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.asad.jobsportal.data.Injection
import com.asad.jobsportal.ui.detail.DetailJobViewModel
import com.asad.jobsportal.ui.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(Injection.provideRepository()) as T
        }
        else if (modelClass.isAssignableFrom(DetailJobViewModel::class.java)) {
            return DetailJobViewModel(Injection.provideRepository()) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}