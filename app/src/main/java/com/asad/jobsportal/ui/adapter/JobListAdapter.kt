package com.asad.jobsportal.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.asad.jobsportal.R
import com.asad.jobsportal.data.network.response.ResponseJobsItem
import com.asad.jobsportal.databinding.ItemJobBinding
import com.asad.jobsportal.ui.detail.DetailJobActivity
import com.bumptech.glide.Glide

class JobListAdapter : PagingDataAdapter<ResponseJobsItem, JobListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val job = getItem(position)
        if (job != null){
            holder.bind(job)
        }
//        if (job == null) {
//            holder.itemView.visibility = View.GONE
//        }else{
//            holder.bind(job)
//        }
    }

    class MyViewHolder(private val binding: ItemJobBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data : ResponseJobsItem){
            binding.apply {
                Glide.with(itemView)
                    .load(data.companyLogo)
                    .placeholder(R.drawable.image_available)
                    .error(R.drawable.no_image_2)
                    .into(imgCompanyLogo)
                tvCompany.text = data.company
                tvLocation.text = data.location
                tvTitle.text = data.title
            }
            itemView.setOnClickListener {
                val intent = Intent(binding.root.context, DetailJobActivity::class.java)
                intent.putExtra(DetailJobActivity.jobId, data.id)
                binding.root.context.startActivity(intent)
            }
        }
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResponseJobsItem>(){
            override fun areItemsTheSame(oldItem: ResponseJobsItem, newItem: ResponseJobsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ResponseJobsItem, newItem: ResponseJobsItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}