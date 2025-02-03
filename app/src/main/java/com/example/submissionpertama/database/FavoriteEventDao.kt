package com.example.submissionpertama.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteEvent: FavoriteEvent)

    @Update
    fun update(favoriteEvent: FavoriteEvent)

    @Delete
    fun delete(favoriteEvent: FavoriteEvent)

    @Query("SELECT * from favorite_events ORDER BY id ASC")
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>>

    @Query("SELECT * from favorite_events WHERE id = :id")
    fun getFavoriteEventById(id: Int): LiveData<FavoriteEvent>
}