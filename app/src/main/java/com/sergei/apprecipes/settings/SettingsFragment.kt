package com.sergei.apprecipes.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.DropDownPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.sergei.apprecipes.R
import com.sergei.apprecipes.RecipesApplication
import com.sergei.apprecipes.database.RecipeLocalDao
import kotlinx.coroutines.coroutineScope

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Getting users list
        /*val usersSet = sharedPrefs.getStringSet("users_dropdown", null)

        // Checking if users list is empty and adding default user
        if (usersSet == null || usersSet.isEmpty()) {
            sharedPrefs.edit().putStringSet("users_dropdown", setOf("Default User")).apply()
        }*/

        // Setting up user choice
        /*val usersDropDown = findPreference<DropDownPreference>("users_dropdown")
        usersDropDown?.entries = sharedPrefs
            .getStringSet("users_dropdown", null)?.toTypedArray() ?: arrayOf("Error")*/

        /*val usersDropDown = DropDownPreference(requireContext()).apply {
            key = "users_dropdown"
            title = "Current user"
            entries = sharedPrefs
                .getStringSet("users", null)?.toTypedArray() ?: arrayOf("Error")
        }*/

    }
}