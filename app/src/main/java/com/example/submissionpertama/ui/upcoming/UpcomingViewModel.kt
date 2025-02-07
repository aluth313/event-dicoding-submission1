package com.example.submissionpertama.ui.upcoming

import androidx.lifecycle.ViewModel
import com.example.submissionpertama.data.EventRepository

class UpcomingViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun fetchUpcomingEvents(q: String = "") = eventRepository.fetchEvents("1",q)
}