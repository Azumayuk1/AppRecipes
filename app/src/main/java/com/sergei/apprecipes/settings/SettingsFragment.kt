package com.sergei.apprecipes.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.DropDownPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.sergei.apprecipes.LoginActivity
import com.sergei.apprecipes.R
import com.sergei.apprecipes.RecipesApplication
import com.sergei.apprecipes.database.RecipeLocalDao
import kotlinx.coroutines.coroutineScope

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val userGoogle: DropDownPreference? = findPreference<DropDownPreference>("user_google")
        userGoogle?.setOnPreferenceClickListener {
            // TODO: Firebase auth in settings
            // Testing Firebase auth
            val intent: Intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            return@setOnPreferenceClickListener true
        }
    }
}