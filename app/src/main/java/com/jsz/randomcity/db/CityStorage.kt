package com.jsz.randomcity.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CityStorage {
    @Query("SELECT * FROM city")
    fun getAll(): List<DbCity>

    @Insert
    fun insert(city: DbCity)
}
