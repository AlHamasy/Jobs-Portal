package com.asad.jobsportal.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.asad.jobsportal.R
import com.asad.jobsportal.databinding.FragmentHomeBinding
import com.asad.jobsportal.ui.adapter.JobListAdapter
import com.asad.jobsportal.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: HomeViewModel by viewModels {
        ViewModelFactory()
    }
    private var location = ""
    private var isFulltime = false
    private var search = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        showJobs()
        showFilter()
        setupSearchView()

        return root
    }

    private fun setupSearchView() {
        binding.svJob.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search = query?:""
                searchJobs()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }

    private fun searchJobs(){
        val jobListAdapter = JobListAdapter()
        binding.rvJob.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = jobListAdapter
        }
        mainViewModel.searchJobs(search, location, isFulltime).observe(this) {
            Log.d("SearchJobs", "$search, $location, $isFulltime")
            jobListAdapter.submitData(lifecycle, it)
        }
    }

    private fun showJobs(){
        val jobListAdapter = JobListAdapter()
        binding.rvJob.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = jobListAdapter
        }
        mainViewModel.getJobs.observe(this) {
            jobListAdapter.submitData(lifecycle, it)
        }
    }

    private fun showFilter(){

        binding.imgBtnArrow.setOnClickListener {
            if (binding.rlFilterBox.visibility == View.GONE){
                showFilterBox()
            }
            else{
                hideFilterBox()
            }
        }

        binding.btnApplyFilter.setOnClickListener {
            isFulltime = binding.swFulltime.isChecked
            location = binding.edtLocation.text.toString()
            searchJobs()
            hideFilterBox()
        }
    }

    private fun showFilterBox(){
        binding.rlFilterBox.visibility = View.VISIBLE
        binding.imgBtnArrow.setImageDrawable(getDrawable(requireContext(),R.drawable.baseline_keyboard_arrow_up_24))
    }

    private fun hideFilterBox(){
        binding.rlFilterBox.visibility = View.GONE
        binding.imgBtnArrow.setImageDrawable(getDrawable(requireContext(),R.drawable.baseline_keyboard_arrow_down_24))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}