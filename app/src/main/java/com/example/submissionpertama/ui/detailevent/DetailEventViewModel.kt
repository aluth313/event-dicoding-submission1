package com.example.submissionpertama.ui.detailevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionpertama.data.EventRepository
import com.example.submissionpertama.data.local.entity.FavoriteEventEntity
import com.example.submissionpertama.data.FavoriteEventRepository
import kotlinx.coroutines.launch

class DetailEventViewModel(
    private val eventRepository: EventRepository,
    private val favoriteEventRepository: FavoriteEventRepository
) : ViewModel() {
    fun fetchDetailEvent(id: Int) = eventRepository.getDetailEvent(id)

    fun insert(favoriteEventEntity: FavoriteEventEntity) {
        viewModelScope.launch {
            favoriteEventRepository.insert(favoriteEventEntity)
        }
    }

    fun getFavoriteEventById(id: Int): LiveData<FavoriteEventEntity> =
        favoriteEventRepository.getFavoriteEventById(id)

    fun delete(favoriteEventEntity: FavoriteEventEntity) {
        viewModelScope.launch {
            favoriteEventRepository.delete(favoriteEventEntity)
        }
    }
}