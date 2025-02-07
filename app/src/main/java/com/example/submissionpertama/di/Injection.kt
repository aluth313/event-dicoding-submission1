package com.example.submissionpertama.di

import android.content.Context
import com.example.submissionpertama.data.EventRepository
import com.example.submissionpertama.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        return EventRepository.getInstance(apiService)
    }
}