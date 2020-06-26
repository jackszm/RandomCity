package com.jsz.randomcity.main

import androidx.annotation.ColorRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jsz.randomcity.db.CityStorage
import com.jsz.randomcity.db.DbCity
import com.jsz.randomcity.mapColor
import com.jsz.randomcity.mapPosition
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val cityStorage: CityStorage,
    private val navigator: MainNavigator
) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    private val cities: MutableLiveData<List<City>> by lazy {
        MutableLiveData<List<City>>().also {
            loadCities()
        }
    }

    fun observeCities(): LiveData<List<City>> = cities

    private fun loadCities() {
        disposables += cityStorage.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.toCity().sortedBy { city -> city.name } }
            .subscribeBy(
                onNext = {
                    cities.value = it
                }
            )

    }

    fun onCityClicked(city: City) {
        navigator.toDetails(
            city.name,
            city.color,
            city.position.first,
            city.position.second
        )
    }

    private fun List<DbCity>.toCity(): List<City> = map { dbCity ->
        City(
            name = dbCity.name,
            timeStamp = dateFormatter.format(Date(dbCity.timestamp)),
            color = mapColor(dbCity.color),
            position = mapPosition(dbCity.name)
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    data class City(
        val name: String,
        val timeStamp: String,
        @ColorRes val color: Int,
        val position: Pair<Double, Double>
    )
}


