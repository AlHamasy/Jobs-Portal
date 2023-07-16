package com.asad.jobsportal.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.asad.jobsportal.data.network.ApiService
import com.asad.jobsportal.data.network.response.ResponseJobsItem

class JobsPagingSource(private val apiService: ApiService): PagingSource<Int, ResponseJobsItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseJobsItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseJobs = apiService.getJobs(page)
            LoadResult.Page(
                data = responseJobs,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseJobs.isNullOrEmpty()) null else page + 1
            )
        }
        catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResponseJobsItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}