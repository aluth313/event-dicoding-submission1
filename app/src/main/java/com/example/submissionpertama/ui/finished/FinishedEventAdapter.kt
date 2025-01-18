package com.example.submissionpertama.ui.finished

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionpertama.data.response.EventsItem
import com.example.submissionpertama.databinding.ItemFinishedEventBinding

class FinishedEventAdapter : ListAdapter<EventsItem, FinishedEventAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFinishedEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class MyViewHolder(val binding: ItemFinishedEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventsItem){
            binding.tvTitleFinishedEvent.text = event.name
            binding.tvSummaryFinishedEvent.text = event.summary
            Glide.with(itemView.context)
                .load(event.imageLogo)
                .into(binding.ivImageFinishedEvent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventsItem>() {
            override fun areItemsTheSame(oldItem: EventsItem, newItem: EventsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: EventsItem, newItem: EventsItem): Boolean {
                return oldItem == newItem
            }

        }
    }

}