package com.example.submissionpertama.ui.home

import androidx.lifecycle.ViewModel
import com.example.submissionpertama.data.EventRepository

class HomeViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun getEvents(active: String) = eventRepository.fetchEvents(active)
    fun getUpcomingEvents(active: String) = eventRepository.fetchEvents(active)
}