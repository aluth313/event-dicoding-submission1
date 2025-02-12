package com.example.submissionpertama.ui.detailevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionpertama.data.EventRepository
import com.example.submissionpertama.database.FavoriteEvent
import com.example.submissionpertama.repository.FavoriteEventRepository

class DetailEventViewModel(
    private val eventRepository: EventRepository,
    private val favoriteEventRepository: FavoriteEventRepository
) : ViewModel() {
    fun fetchDetailEvent(id: Int) = eventRepository.getDetailEvent(id)

    fun insert(favoriteEvent: FavoriteEvent) {
        favoriteEventRepository.insert(favoriteEvent)
    }

    fun getFavoriteEventById(id: Int): LiveData<FavoriteEvent> =
        favoriteEventRepository.getFavoriteEventById(id)

    fun delete(favoriteEvent: FavoriteEvent) {
        favoriteEventRepository.delete(favoriteEvent)
    }
}