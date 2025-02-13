package com.example.submissionpertama.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submissionpertama.data.local.entity.FavoriteEventEntity
import com.example.submissionpertama.data.local.room.FavoriteEventDao
import com.example.submissionpertama.data.local.room.FavoriteEventRoomDatabase

class FavoriteEventRepository(application: Application) {
    private val mFavoriteEventDao: FavoriteEventDao

    init {
        val db = FavoriteEventRoomDatabase.getDatabase(application)
        mFavoriteEventDao = db.favoriteEventDao()
    }

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEventEntity>> =
        mFavoriteEventDao.getAllFavoriteEvents()

    fun getFavoriteEventById(id: Int): LiveData<FavoriteEventEntity> =
        mFavoriteEventDao.getFavoriteEventById(id)

    suspend fun insert(favoriteEventEntity: FavoriteEventEntity) {
        mFavoriteEventDao.insert(favoriteEventEntity)
    }

    suspend fun delete(favoriteEventEntity: FavoriteEventEntity) {
        mFavoriteEventDao.delete(favoriteEventEntity)
    }

}