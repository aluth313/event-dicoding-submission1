package com.example.submissionpertama.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionpertama.data.remote.response.EventItem
import com.example.submissionpertama.databinding.ItemUpcomingHomeBinding
import com.example.submissionpertama.ui.detailevent.DetailEventActivity

class UpcomingEventHomeAdapter : ListAdapter<EventItem, UpcomingEventHomeAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUpcomingHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class MyViewHolder(private val binding: ItemUpcomingHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventItem){
            binding.tvTitleEventHome.text = event.name
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.ivImageEventHome)
            binding.cardEventHome.setOnClickListener{ view ->
                val moveToDetail = Intent(view.context, DetailEventActivity::class.java)
                moveToDetail.putExtra("event_id", event.id)
                view.context.startActivity(moveToDetail)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventItem>() {
            override fun areItemsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
                return oldItem == newItem
            }

        }
    }

}