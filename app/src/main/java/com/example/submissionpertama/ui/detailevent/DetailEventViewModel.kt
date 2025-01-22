package com.example.submissionpertama.ui.detailevent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionpertama.data.response.DetailEventResponse
import com.example.submissionpertama.data.response.EventItem
import com.example.submissionpertama.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEventViewModel : ViewModel() {
    private val _detailEvent = MutableLiveData<EventItem>()
    val detailEvent: LiveData<EventItem> = _detailEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    companion object {
        private const val TAG = "DetailEventViewModel"
    }

    fun fetchDetailEvent(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailEvent(id)
        client.enqueue(object : Callback<DetailEventResponse> {
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailEvent.value = response.body()?.event
                } else {
                    val msgError = "onFailure: ${response.message()}"
                    _errorMessage.value = msgError
                    Log.e(TAG, msgError)
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = false
                val msgError = "onFailure: ${t.message.toString()}"
                _errorMessage.value = msgError
                Log.e(TAG, msgError)
            }
        })
    }
}