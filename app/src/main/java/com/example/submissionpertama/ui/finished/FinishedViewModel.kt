package com.example.submissionpertama.ui.finished

import androidx.lifecycle.ViewModel
import com.example.submissionpertama.data.EventRepository

class FinishedViewModel(private val eventRepository: EventRepository) : ViewModel() {
    fun fetchFinishedEvents(q: String = "") = eventRepository.fetchEvents("0",q)
}