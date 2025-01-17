package com.example.submissionpertama.data.retrofit

import com.example.submissionpertama.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/events?active=1")
    fun getEvents(): Call<EventResponse>
}