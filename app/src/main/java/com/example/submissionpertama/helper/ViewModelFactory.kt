package com.example.submissionpertama.helper

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionpertama.MainViewModel
import com.example.submissionpertama.SettingPreferences
import com.example.submissionpertama.data.EventRepository
import com.example.submissionpertama.di.Injection
import com.example.submissionpertama.ui.detailevent.DetailEventViewModel
import com.example.submissionpertama.ui.favorite.FavoriteViewModel
import com.example.submissionpertama.ui.finished.FinishedViewModel
import com.example.submissionpertama.ui.upcoming.UpcomingViewModel

class ViewModelFactory private constructor(
    private val mApplication: Application,
    private val pref: SettingPreferences? = null,
    private val eventRepository: EventRepository,
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(
            application: Application? = null,
            pref: SettingPreferences? = null,
            context: Context,
        ): ViewModelFactory? {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: application?.let {
                    ViewModelFactory(
                        it, pref,
                        Injection.provideRepository(context)).also { INSTANCE = it }
                }
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

            modelClass.isAssignableFrom(FinishedViewModel::class.java) -> {
                return FinishedViewModel(eventRepository) as T
            }

            modelClass.isAssignableFrom(UpcomingViewModel::class.java) -> {
                return UpcomingViewModel(eventRepository) as T
            }

//            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
//                return HomeViewModel(eventRepository) as T
//            }

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