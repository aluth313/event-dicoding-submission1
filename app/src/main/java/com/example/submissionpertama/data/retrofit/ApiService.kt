package com.example.submissionpertama.data.retrofit

import com.example.submissionpertama.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/events")
    fun getEvents(
        @Query("active") active: String,
        @Query("q") q: String,
    ): Call<EventResponse>
}