package com.example.submissionpertama.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailEventResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("event")
	val event: EventItem
)

