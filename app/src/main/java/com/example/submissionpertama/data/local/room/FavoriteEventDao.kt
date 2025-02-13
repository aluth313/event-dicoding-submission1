package com.example.submissionpertama.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submissionpertama.data.local.entity.FavoriteEventEntity

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteEventEntity: FavoriteEventEntity)

    @Delete
    suspend fun delete(favoriteEventEntity: FavoriteEventEntity)

    @Query("SELECT * from favorite_events ORDER BY id ASC")
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEventEntity>>

    @Query("SELECT * from favorite_events WHERE id = :id")
    fun getFavoriteEventById(id: Int): LiveData<FavoriteEventEntity>
}