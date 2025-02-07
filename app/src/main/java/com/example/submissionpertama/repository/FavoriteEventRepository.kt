package com.example.submissionpertama.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submissionpertama.database.FavoriteEvent
import com.example.submissionpertama.database.FavoriteEventDao
import com.example.submissionpertama.database.FavoriteEventRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteEventRepository(application: Application) {
    private val mFavoriteEventDao: FavoriteEventDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteEventRoomDatabase.getDatabase(application)
        mFavoriteEventDao = db.favoriteEventDao()
    }

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> =
        mFavoriteEventDao.getAllFavoriteEvents()

    fun getFavoriteEventById(id: Int): LiveData<FavoriteEvent> =
        mFavoriteEventDao.getFavoriteEventById(id)

    fun insert(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventDao.insert(favoriteEvent) }
    }

    fun delete(favoriteEvent: FavoriteEvent) {
        executorService.execute { mFavoriteEventDao.delete(favoriteEvent) }
    }

}