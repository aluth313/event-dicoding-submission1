package com.example.submissionpertama.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionpertama.MainViewModel
import com.example.submissionpertama.SettingPreferences
import com.example.submissionpertama.ui.detailevent.DetailEventViewModel
import com.example.submissionpertama.ui.favorite.FavoriteViewModel

class ViewModelFactory private constructor(
    private val mApplication: Application,
    private val pref: SettingPreferences? = null
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            application: Application,
            pref: SettingPreferences? = null
        ): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(application, pref).also { INSTANCE = it }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(DetailEventViewModel::class.java) -> {
                return DetailEventViewModel(mApplication) as T
            }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                return FavoriteViewModel(mApplication) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                if (pref == null) {
                    throw IllegalArgumentException("SettingPreferences is required for MainViewModel")
                }
                return MainViewModel(pref) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}