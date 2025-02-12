package com.example.submissionpertama.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.submissionpertama.data.remote.response.DetailEventResponse
import com.example.submissionpertama.data.remote.response.EventItem
import com.example.submissionpertama.data.remote.response.EventResponse
import com.example.submissionpertama.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventRepository private constructor() {
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
        val result = MediatorLiveData<Result<List<EventItem>>>()
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

    fun getDetailEvent(id: Int): LiveData<Result<EventItem>> {
        val resultDetail = MediatorLiveData<Result<EventItem>>()
        resultDetail.value = Result.Loading
        val client = ApiConfig.getApiService().getDetailEvent(id)
        client.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(call: Call<DetailEventResponse>, response: Response<DetailEventResponse>) {
                if (response.isSuccessful){
                    resultDetail.value = response.body()?.let { Result.Success(it.event) }
                } else {
                    val msgError = "onFailure: ${response.message()}"
                    resultDetail.value = Result.Error(msgError)
                    Log.e(TAG, msgError)
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                val msgError = "onFailure: ${t.message.toString()}"
                resultDetail.value = Result.Error(msgError)
                Log.e(TAG, msgError)
            }
        })
        return resultDetail
    }
}