package com.jsz.randomcity.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface CityStorage {
    @Query("SELECT * FROM city")
    fun getAll(): Flowable<List<DbCity>>

    @Insert
    fun insert(city: DbCity)
}
