package com.example.submissionpertama.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.submissionpertama.database.FavoriteEvent

class FavoriteEventDiffCallback(private val oldFavoriteEventList: List<FavoriteEvent>, private val newFavoriteEventList: List<FavoriteEvent>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteEventList.size

    override fun getNewListSize(): Int = newFavoriteEventList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteEventList[oldItemPosition].id == newFavoriteEventList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteEvent = oldFavoriteEventList[oldItemPosition]
        val newFavoriteEvent = newFavoriteEventList[newItemPosition]

        return oldFavoriteEvent.name == newFavoriteEvent.name && oldFavoriteEvent.imageLogo == newFavoriteEvent.imageLogo
    }
}