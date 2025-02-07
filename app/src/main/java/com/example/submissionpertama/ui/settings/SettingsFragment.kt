package com.example.submissionpertama.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.submissionpertama.MainViewModel
import com.example.submissionpertama.R
import com.example.submissionpertama.SettingPreferences
import com.example.submissionpertama.dataStore
import com.example.submissionpertama.helper.ViewModelFactory

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var DARKMODE: String
    private lateinit var isDarkModePreference : SwitchPreference
    private lateinit var mainViewModel: MainViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val factory = ViewModelFactory.getInstance(requireActivity().application, pref, requireActivity())
        mainViewModel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun setSummaries() {
        val sh = preferenceManager.sharedPreferences
        isDarkModePreference.isChecked = sh?.getBoolean(DARKMODE, false) ?: false
    }

    private fun init(){
        DARKMODE = resources.getString(R.string.key_dark_mode)
        isDarkModePreference = findPreference<SwitchPreference>(DARKMODE) as SwitchPreference
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == DARKMODE){
            val isDarkModeActive = sharedPreferences?.getBoolean(DARKMODE, false) ?: false
            isDarkModePreference.isChecked = isDarkModeActive
            mainViewModel.saveThemeSetting(isDarkModeActive)
            if (isDarkModePreference.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}