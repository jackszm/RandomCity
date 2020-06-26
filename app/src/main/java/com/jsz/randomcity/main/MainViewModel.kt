package com.jsz.randomcity.main

import androidx.annotation.ColorRes
import com.jsz.randomcity.AppNavigator
import com.jsz.randomcity.db.CityStorage
import com.jsz.randomcity.db.DbCity
import com.jsz.randomcity.mapColor
import com.jsz.randomcity.mapPosition
import com.jsz.randomcity.utils.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    private val cityStorage: CityStorage,
    private val navigator: AppNavigator
) : BaseViewModel<List<MainViewModel.City>>(emptyList()) {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    init {
        loadCities()
    }

    private fun loadCities() {
        disposables += cityStorage.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.toCity().sortedBy { city -> city.name } }
            .subscribeBy(
                onNext = { setState { it } }
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
