package com.example.submissionpertama.di

import com.example.submissionpertama.data.EventRepository

object Injection {
    fun provideRepository(): EventRepository {
        return EventRepository.getInstance()
    }
}