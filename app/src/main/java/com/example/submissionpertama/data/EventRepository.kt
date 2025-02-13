package com.example.submissionpertama.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.submissionpertama.data.remote.response.EventItem
import com.example.submissionpertama.data.remote.retrofit.ApiService

class EventRepository private constructor(
    private val apiService: ApiService,
) {
    companion object {
        private const val TAG = "EventRepository"

        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService)
            }.also { instance = it }
    }

    fun fetchEvents(active: String = "0", q: String = ""): LiveData<Result<List<EventItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getEvents(active = active, q = q)
            emit(Result.Success(response.listEvents))
        } catch (e: Exception){
            Log.e(TAG, "fetchEvents: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetailEvent(id: Int): LiveData<Result<EventItem>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailEvent(id)
            emit(Result.Success(response.event))
        } catch (e: Exception){
            Log.e(TAG, "fetchEvents: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }
}