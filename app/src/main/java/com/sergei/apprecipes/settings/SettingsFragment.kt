package com.sergei.apprecipes.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.DropDownPreference
import androidx.preference.PreferenceFragmentCompat
import com.sergei.apprecipes.LoginActivity
import com.sergei.apprecipes.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val userGoogle: DropDownPreference? = findPreference<DropDownPreference>("user_google")
        userGoogle?.setOnPreferenceClickListener {
            // Firebase auth
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            return@setOnPreferenceClickListener true
        }
    }
}