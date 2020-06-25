package com.jsz.randomcity

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.room.Room
import com.jsz.randomcity.db.AppDatabase
import com.jsz.randomcity.db.DbCity
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class RandomCityApp : Application() {

    private val producer = SomeProducer()

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        initDb()
        observeProcessLifecycle()
        storeProducerEvents()
    }

    private fun initDb() {
        db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "database")
            .build()
    }

    private fun observeProcessLifecycle() {
        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(producer)
    }

    private fun storeProducerEvents() {
        producerEvents()
            .subscribeOn(Schedulers.io())
            .doOnNext {
                db.cityStorage().insert(DbCity(it.city, it.color, System.currentTimeMillis()))
            }
            .subscribe()
    }

    fun producerEvents() = producer.events

}
