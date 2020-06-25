package com.jsz.randomcity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class DbCity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long
) {
    @PrimaryKey(autoGenerate = true)
    var cityId: Int = 0
}
