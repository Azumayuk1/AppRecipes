package com.sergei.apprecipes.database

import android.app.Application

class RecipesApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}