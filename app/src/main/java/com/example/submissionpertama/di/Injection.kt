package com.example.submissionpertama.di

import android.app.Application
import com.example.submissionpertama.data.EventRepository
import com.example.submissionpertama.repository.FavoriteEventRepository

object Injection {
    fun provideRepository(): EventRepository {
        return EventRepository.getInstance()
    }

    fun provideFavoriteEventRepository(application: Application): FavoriteEventRepository {
        return FavoriteEventRepository(application)
    }
}