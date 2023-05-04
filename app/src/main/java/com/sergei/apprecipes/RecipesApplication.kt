package com.sergei.apprecipes

import android.app.Application
import com.sergei.apprecipes.database.AppDatabase
import com.sergei.apprecipes.network.SpoonacularApi

class RecipesApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}