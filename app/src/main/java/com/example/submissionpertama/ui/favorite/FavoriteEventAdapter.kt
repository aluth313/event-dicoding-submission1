package com.example.submissionpertama.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionpertama.data.local.entity.FavoriteEventEntity
import com.example.submissionpertama.databinding.ItemFavoriteEventBinding
import com.example.submissionpertama.utils.helper.FavoriteEventDiffCallback
import com.example.submissionpertama.ui.detailevent.DetailEventActivity

class FavoriteEventAdapter : RecyclerView.Adapter<FavoriteEventAdapter.FavoriteEventViewHolder>() {
    private val listFavoriteEventEntities = ArrayList<FavoriteEventEntity>()
    fun setListFavoriteEvents(listFavoriteEventEntities: List<FavoriteEventEntity>){
        val diffCallback = FavoriteEventDiffCallback(this.listFavoriteEventEntities, listFavoriteEventEntities)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteEventEntities.clear()
        this.listFavoriteEventEntities.addAll(listFavoriteEventEntities)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        val binding = ItemFavoriteEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteEventViewHolder, position: Int) {
        holder.bind(listFavoriteEventEntities[position])
    }

    override fun getItemCount(): Int {
        return listFavoriteEventEntities.size
    }

    inner class FavoriteEventViewHolder(private val binding: ItemFavoriteEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEventEntity: FavoriteEventEntity){
            binding.tvTitleFavoriteEvent.text = favoriteEventEntity.name
            Glide.with(itemView.context)
                .load(favoriteEventEntity.imageLogo)
                .into(binding.ivImageFavoriteEvent)
            binding.cardFavoriteEvent.setOnClickListener{ view ->
                val moveToDetail = Intent(view.context, DetailEventActivity::class.java)
                moveToDetail.putExtra("event_id", favoriteEventEntity.id)
                view.context.startActivity(moveToDetail)
            }
        }
    }
}