package com.jsz.randomcity

import androidx.annotation.ColorRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit

class SomeProducer private constructor() : LifecycleObserver {

    private var disposable : Disposable? = null
    private val subject = PublishSubject.create<ColorfulCity>()
    private val random = Random(System.currentTimeMillis())

    private object HOLDER {
        val INSTANCE = SomeProducer()
    }

    init {
        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        disposable?.dispose()
        disposable = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
         disposable = Observable.interval(5, TimeUnit.SECONDS)
            .map {
                ColorfulCity(
                    city = cities[random.nextInt(cities.size)],
                    color = colors[random.nextInt(colors.size)]
                )
            }
            .subscribeOn(Schedulers.io())
            .subscribe { subject.onNext(it) }
    }

    companion object {
        val producerEvents: Observable<ColorfulCity> by lazy { HOLDER.INSTANCE.subject }
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

@ColorRes
fun mapColor(color: String): Int {
    return when (color) {
        "Yellow" -> R.color.yellow
        "Green" -> R.color.green
        "Blue" -> R.color.blue
        "Red" -> R.color.red
        "Black" -> R.color.black
        "White" -> R.color.white
        else -> R.color.black
    }
}

fun mapPosition(city: String): Pair<Double, Double> {
    return when (city) {
        "Gdańsk" -> 54.356030 to 18.646120
        "Warszawa" -> 52.237049 to 21.017532
        "Poznań" -> 52.406376 to 16.925167
        "Białystok" -> 53.13333 to 23.16433
        "Wrocław" -> 51.107883 to 17.038538
        "Katowice" -> 50.270908 to 19.039993
        "Kraków" -> 50.049683 to 19.944544
        else -> 52.237049 to 21.017532
    }
}
