package com.example.submissionpertama.data.remote.retrofit

import com.example.submissionpertama.data.remote.response.DetailEventResponse
import com.example.submissionpertama.data.remote.response.EventResponse
import retrofit2.http.*

interface ApiService {
    @GET("/events")
    suspend fun getEvents(
        @Query("active") active: String,
        @Query("q") q: String,
    ): EventResponse

    @GET("/events/{id}")
    suspend fun getDetailEvent(
        @Path("id") id: Int,
    ): DetailEventResponse
}