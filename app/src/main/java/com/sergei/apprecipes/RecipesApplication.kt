package com.sergei.apprecipes

import android.app.Application
import com.sergei.apprecipes.database.AppDatabase

class RecipesApplication : Application() {
    // Initializing the database
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}