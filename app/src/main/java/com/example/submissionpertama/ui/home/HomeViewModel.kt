package com.example.submissionpertama.ui.home

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

class HomeViewModel : ViewModel() {

    private val _listEvent = MutableLiveData<List<EventItem>>()
    val listEvent: LiveData<List<EventItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _listUpcomingEvent = MutableLiveData<List<EventItem>>()
    val listUpcomingEvent: LiveData<List<EventItem>> = _listUpcomingEvent

    private val _isLoadingUpcoming = MutableLiveData<Boolean>()
    val isLoadingUpcoming: LiveData<Boolean> = _isLoadingUpcoming

    private val _errorMessageUpcoming = MutableLiveData<String>()
    val errorMessageUpcoming: LiveData<String> = _errorMessageUpcoming

    companion object {
        private const val TAG = "HomeViewModel"
    }

    init {
        fetchEvents()
        fetchFinishedEvents()
    }

    private fun fetchEvents() {
        _isLoadingUpcoming.value = true
        val client = ApiConfig.getApiService().getEvents(active = "1", q = "")
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoadingUpcoming.value = false
                if (response.isSuccessful){
                    val listEvents = arrayListOf<EventItem>()
                    for ((index, value) in response.body()?.listEvents!!.withIndex()){
                        if (index < 5){
                            listEvents += value
                        } else {
                            break
                        }
                    }
                    _listUpcomingEvent.value = listEvents
                } else {
                    val msgError = "onFailure: ${response.message()}"
                    _errorMessageUpcoming.value = msgError
                    Log.e(TAG, msgError)
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoadingUpcoming.value = false
                val msgError = "onFailure: ${t.message.toString()}"
                _errorMessageUpcoming.value = msgError
                Log.e(TAG, msgError)
            }

        })
    }

    private fun fetchFinishedEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvents(active = "0", q = "")
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val listEvents = arrayListOf<EventItem>()
                    for ((index, value) in response.body()?.listEvents!!.withIndex()){
                        if (index < 5){
                            listEvents += value
                        } else {
                            break
                        }
                    }
                    _listEvent.value = listEvents
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