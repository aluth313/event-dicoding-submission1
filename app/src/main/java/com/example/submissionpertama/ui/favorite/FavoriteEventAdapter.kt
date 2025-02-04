package com.example.submissionpertama.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionpertama.database.FavoriteEvent
import com.example.submissionpertama.databinding.ItemFavoriteEventBinding
import com.example.submissionpertama.helper.FavoriteEventDiffCallback
import com.example.submissionpertama.ui.detailevent.DetailEventActivity

class FavoriteEventAdapter : RecyclerView.Adapter<FavoriteEventAdapter.FavoriteEventViewHolder>() {
    private val listFavoriteEvents = ArrayList<FavoriteEvent>()
    fun setListFavoriteEvents(listFavoriteEvents: List<FavoriteEvent>){
        val diffCallback = FavoriteEventDiffCallback(this.listFavoriteEvents, listFavoriteEvents)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteEvents.clear()
        this.listFavoriteEvents.addAll(listFavoriteEvents)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        val binding = ItemFavoriteEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteEventViewHolder, position: Int) {
        holder.bind(listFavoriteEvents[position])
    }

    override fun getItemCount(): Int {
        return listFavoriteEvents.size
    }

    inner class FavoriteEventViewHolder(private val binding: ItemFavoriteEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEvent: FavoriteEvent){
            binding.tvTitleFavoriteEvent.text = favoriteEvent.name
            Glide.with(itemView.context)
                .load(favoriteEvent.imageLogo)
                .into(binding.ivImageFavoriteEvent)
            binding.cardFavoriteEvent.setOnClickListener{ view ->
                val moveToDetail = Intent(view.context, DetailEventActivity::class.java)
                moveToDetail.putExtra("event_id", favoriteEvent.id)
                view.context.startActivity(moveToDetail)
            }
        }
    }
}