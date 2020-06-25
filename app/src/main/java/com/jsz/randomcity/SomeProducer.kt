package com.jsz.randomcity

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit

class SomeProducer : LifecycleObserver {

        private val disposables: CompositeDisposable = CompositeDisposable()
    private val subject = PublishSubject.create<ColorfulCity>()
    private val random = Random(System.currentTimeMillis())

    val events: Observable<ColorfulCity>
        get() {
            return subject
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        disposables.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        disposables += Observable.interval(5, TimeUnit.SECONDS)
            .map {
                ColorfulCity(
                    city = cities[random.nextInt(cities.size)],
                    color = colors[random.nextInt(colors.size)]
                )
            }
            .subscribeOn(Schedulers.io())
            .subscribe { subject.onNext(it) }
    }
}

private val cities = listOf(
    "Gdańsk",
    "Warszawa",
    "Poznań",
    "Białystok",
    "Wrocław",
    "Katowice",
    "Kraków"
)

private val colors = listOf(
    "Yellow",
    "Green",
    "Blue",
    "Red",
    "Black",
    "White"
)

data class ColorfulCity(
    val city: String,
    val color: String
)
