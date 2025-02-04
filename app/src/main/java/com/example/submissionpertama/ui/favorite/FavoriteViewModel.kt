package com.example.submissionpertama.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionpertama.database.FavoriteEvent
import com.example.submissionpertama.repository.FavoriteEventRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteEventRepository: FavoriteEventRepository =
        FavoriteEventRepository(application)

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> =
        mFavoriteEventRepository.getAllFavoriteEvents()

}