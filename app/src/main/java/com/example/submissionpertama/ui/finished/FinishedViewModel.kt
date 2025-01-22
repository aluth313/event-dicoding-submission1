package com.example.submissionpertama.ui.finished

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionpertama.data.response.EventItem
import com.example.submissionpertama.data.response.EventResponse
import com.example.submissionpertama.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {

    private val _listEvent = MutableLiveData<List<EventItem>>()
    val listEvent: LiveData<List<EventItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    companion object {
        private const val TAG = "FinishedViewModel"
    }

    init {
        fetchFinishedEvents()
    }

    fun fetchFinishedEvents(active: String = "0", q: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(active = active, q = q)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listEvent.value = response.body()?.listEvents
                } else {
                    val msgError = "onFailure: ${response.message()}"
                    _errorMessage.value = msgError
                    Log.e(TAG, msgError)
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                val msgError = "onFailure: ${t.message.toString()}"
                _errorMessage.value = msgError
                Log.e(TAG, msgError)
            }

        })
    }
}