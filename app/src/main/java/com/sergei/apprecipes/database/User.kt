package com.sergei.apprecipes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val username: String
)
