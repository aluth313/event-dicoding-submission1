package com.example.submissionpertama.utils.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.submissionpertama.data.local.entity.FavoriteEventEntity

class FavoriteEventDiffCallback(private val oldFavoriteEventEntityList: List<FavoriteEventEntity>, private val newFavoriteEventEntityList: List<FavoriteEventEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteEventEntityList.size

    override fun getNewListSize(): Int = newFavoriteEventEntityList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteEventEntityList[oldItemPosition].id == newFavoriteEventEntityList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteEvent = oldFavoriteEventEntityList[oldItemPosition]
        val newFavoriteEvent = newFavoriteEventEntityList[newItemPosition]

        return oldFavoriteEvent.name == newFavoriteEvent.name && oldFavoriteEvent.imageLogo == newFavoriteEvent.imageLogo
    }
}