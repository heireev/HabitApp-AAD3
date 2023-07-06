package com.dicoding.habitapp.setting

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.dicoding.habitapp.R
import com.dicoding.habitapp.utils.DarkMode

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            //TODO 11 : Update theme based on value in ListPreference
            val prefTheme = findPreference<ListPreference>(getString(R.string.pref_key_dark))
            prefTheme?.setOnPreferenceChangeListener { _, newValue ->
                updateTheme(
                    when (newValue) {
                        getString(R.string.pref_dark_on) -> DarkMode.ON.value
                        getString(R.string.pref_dark_off) -> DarkMode.OFF.value
                        else -> DarkMode.FOLLOW_SYSTEM.value
                    }
                )
                Toast.makeText(requireContext(), "Update Theme Success", Toast.LENGTH_SHORT).show()
                true
            }
        }

        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}