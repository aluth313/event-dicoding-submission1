package com.example.submissionpertama.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.submissionpertama.MainViewModel
import com.example.submissionpertama.R
import com.example.submissionpertama.SettingPreferences
import com.example.submissionpertama.dataStore
import com.example.submissionpertama.helper.ViewModelFactory
import com.example.submissionpertama.utils.MyWorker
import java.util.concurrent.TimeUnit

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var darkmode: String
    private lateinit var remainder: String
    private lateinit var isDarkModePreference : SwitchPreference
    private lateinit var isRemainder : SwitchPreference
    private lateinit var mainViewModel: MainViewModel
    private lateinit var periodicWorkRequest: PeriodicWorkRequest
    private lateinit var workManager: WorkManager

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        workManager = WorkManager.getInstance(requireActivity())
        init()
        setSummaries()

        val pref = SettingPreferences.getInstance(requireContext().dataStore)
        val factory = ViewModelFactory.getInstance(requireActivity().application, pref)
        mainViewModel = ViewModelProvider(this, factory!!)[MainViewModel::class.java]
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
        isDarkModePreference.isChecked = sh?.getBoolean(darkmode, false) ?: false
        isRemainder.isChecked = sh?.getBoolean(remainder, false) ?: false
    }

    private fun init(){
        darkmode = resources.getString(R.string.key_dark_mode)
        remainder = resources.getString(R.string.key_remainder)
        isDarkModePreference = findPreference<SwitchPreference>(darkmode) as SwitchPreference
        isRemainder = findPreference<SwitchPreference>(remainder) as SwitchPreference
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == darkmode){
            val isDarkModeActive = sharedPreferences?.getBoolean(darkmode, false) ?: false
            isDarkModePreference.isChecked = isDarkModeActive
            mainViewModel.saveThemeSetting(isDarkModeActive)
            if (isDarkModePreference.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        if (key == remainder){
            val isRemainderActive = sharedPreferences?.getBoolean(remainder, false) ?: false
            isRemainder.isChecked = isRemainderActive
            mainViewModel.saveRemainderSetting(isRemainderActive)

            val editor = requireContext().getSharedPreferences("remainder_setting", Context.MODE_PRIVATE).edit()
            editor.putBoolean("remainder", isRemainderActive)
            editor.apply()

            if (isRemainder.isChecked) {
                val constraints = Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
                periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 1, TimeUnit.DAYS)
                    .setConstraints(constraints)
                    .build()
                workManager.enqueue(periodicWorkRequest)
                workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id)
                    .observe(viewLifecycleOwner) { workInfo ->
                        isRemainder.isEnabled = false
                        if (workInfo?.state == WorkInfo.State.ENQUEUED || workInfo?.state == WorkInfo.State.CANCELLED){
                            isRemainder.isEnabled = true
                        }
                    }
            } else {
                workManager.cancelAllWork()
            }
        }
    }
}