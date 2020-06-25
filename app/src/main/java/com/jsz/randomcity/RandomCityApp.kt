package com.jsz.randomcity

import android.annotation.SuppressLint
import android.app.Application
import androidx.room.Room
import com.jsz.randomcity.db.AppDatabase
import com.jsz.randomcity.db.DbCity
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class RandomCityApp : Application() {


    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        initDb()
        storeProducerEvents()
    }

    private fun initDb() {
        db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "database")
            .build()
    }

    private fun storeProducerEvents() {
        SomeProducer.producerEvents
            .subscribeOn(Schedulers.io())
            .doOnNext {
                db.cityStorage().insert(DbCity(it.city, it.color, System.currentTimeMillis()))
            }
            .subscribe()
    }
}
