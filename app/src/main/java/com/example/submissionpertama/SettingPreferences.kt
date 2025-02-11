package com.example.submissionpertama

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>){
    private val themekey = booleanPreferencesKey("theme_setting")
    private val remainderkey = booleanPreferencesKey("remainder_setting")

    fun getThemeSetting(): Flow<Boolean>{
        return dataStore.data.map { preferences ->
            preferences[themekey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean){
        dataStore.edit { preferences ->
            preferences[themekey] = isDarkModeActive
        }
    }

    suspend fun saveRemainderSetting(isRemainderActive: Boolean){
        dataStore.edit { preferences ->
            preferences[remainderkey] = isRemainderActive
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}