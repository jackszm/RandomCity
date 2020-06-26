package com.jsz.randomcity.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jsz.randomcity.AppNavigator
import com.jsz.randomcity.SomeProducer
import com.jsz.randomcity.utils.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class SplashViewModel(
    private val navigator: AppNavigator
) : BaseViewModel<Unit>(Unit) {

    init {
        disposables += SomeProducer.producerEvents
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .singleOrError()
            .subscribeBy(
                onSuccess = { navigator.toMain() }
            )
    }

}

class SplashViewModelFactory(
    private val navigator: AppNavigator
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(navigator) as T
    }

}
