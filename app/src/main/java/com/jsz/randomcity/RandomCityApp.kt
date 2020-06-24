package com.jsz.randomcity

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.room.Room
import com.jsz.randomcity.db.AppDatabase

class RandomCityApp : Application() {

    lateinit var db: AppDatabase



    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database")
            .build()

        val applicationObserver =  ApplicationObserver(db.cityStorage())

        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(applicationObserver)
    }

}
