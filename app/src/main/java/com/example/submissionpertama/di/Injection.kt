package com.example.submissionpertama.di

import android.app.Application
import com.example.submissionpertama.data.EventRepository
import com.example.submissionpertama.data.FavoriteEventRepository
import com.example.submissionpertama.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(): EventRepository {
        val apiService = ApiConfig.getApiService()
        return EventRepository.getInstance(apiService)
    }

    fun provideFavoriteEventRepository(application: Application): FavoriteEventRepository {
        return FavoriteEventRepository(application)
    }
}