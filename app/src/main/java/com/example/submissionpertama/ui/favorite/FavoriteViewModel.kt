package com.example.submissionpertama.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionpertama.data.local.entity.FavoriteEventEntity
import com.example.submissionpertama.data.FavoriteEventRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteEventRepository: FavoriteEventRepository =
        FavoriteEventRepository(application)

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEventEntity>> =
        mFavoriteEventRepository.getAllFavoriteEvents()

}