package com.jsz.randomcity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jsz.randomcity.db.CityStorage

class MainViewModelFactory(
    private val cityStorage: CityStorage,
    private val navigator: MainNavigator
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(cityStorage, navigator) as T
    }
}
