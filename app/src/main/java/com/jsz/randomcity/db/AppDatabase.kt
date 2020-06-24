package com.jsz.randomcity.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        DbCity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityStorage(): CityStorage
}
