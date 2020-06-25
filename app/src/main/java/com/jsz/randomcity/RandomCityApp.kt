package com.jsz.randomcity

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.room.Room
import com.jsz.randomcity.db.AppDatabase
import com.jsz.randomcity.db.DbCity
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class RandomCityApp : Application() {

    private val producer = SomeProducer()

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "database")
            .build()

        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(producer)

        producer.events
            .subscribeOn(Schedulers.io())
            .doOnNext {
                db.cityStorage().insert(
                    DbCity(
                        name = it.city,
                        color = it.color,
                        timestamp = System.currentTimeMillis()
                    )
                )
            }
            .subscribeBy(
                onNext = { Log.e("!!!", "Saving") }
            )


    }

    fun producerEvents() = producer.events

}
