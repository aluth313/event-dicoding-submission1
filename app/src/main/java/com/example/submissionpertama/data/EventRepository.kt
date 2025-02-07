package com.example.submissionpertama.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.submissionpertama.data.remote.response.EventItem
import com.example.submissionpertama.data.remote.response.EventResponse
import com.example.submissionpertama.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository private constructor() {
    private val result = MediatorLiveData<Result<List<EventItem>>>()

    companion object {
        private const val TAG = "EventRepository"

        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository()
            }.also { instance = it }
    }

    fun fetchEvents(active: String = "0", q: String = ""): LiveData<Result<List<EventItem>>> {
        result.value = Result.Loading
        val client = ApiConfig.getApiService().getEvents(active = active, q = q)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful){
                    result.value = response.body()?.let { Result.Success(it.listEvents) }
                } else {
                    val msgError = "onFailure: ${response.message()}"
                    result.value = Result.Error(msgError)
                    Log.e(TAG, msgError)
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                val msgError = "onFailure: ${t.message.toString()}"
                result.value = Result.Error(msgError)
                Log.e(TAG, msgError)
            }
        })
        return result
    }
}