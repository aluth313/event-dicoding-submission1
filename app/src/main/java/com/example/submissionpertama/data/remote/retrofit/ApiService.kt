package com.example.submissionpertama.data.remote.retrofit

import com.example.submissionpertama.data.remote.response.DetailEventResponse
import com.example.submissionpertama.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("/events")
    fun getEvents(
        @Query("active") active: String,
        @Query("q") q: String,
    ): Call<EventResponse>

    @GET("/events/{id}")
    fun getDetailEvent(
        @Path("id") id: Int,
    ): Call<DetailEventResponse>
}