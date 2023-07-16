package com.asad.jobsportal.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.asad.jobsportal.databinding.ActivityDetailJobBinding
import com.asad.jobsportal.utils.ViewModelFactory

class DetailJobActivity : AppCompatActivity() {

    private val mainViewModel: DetailJobViewModel by viewModels {
        ViewModelFactory()
    }
    private lateinit var binding : ActivityDetailJobBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Job Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intentData = intent.getStringExtra(jobId)
        getDetail(intentData?:"")
    }

    private fun getDetail(intentData: String) {
        mainViewModel.getDetailJob(intentData)
        mainViewModel.resultDetailJob.observe(this) { item ->
            binding.tvCompanyName.text = item.company
            binding.tvLocation.text = item.location
            binding.tvWebsite.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.companyUrl)
                startActivity(i)
            }
            binding.tvTitle.text = item.title
            binding.tvType.text = item.type
            binding.tvDescription.text = Html.fromHtml(item.description)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val jobId = "id"
    }
}