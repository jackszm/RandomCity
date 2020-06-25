package com.jsz.randomcity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class SplashActivity : AppCompatActivity() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listenForFirstProducerEmission()
    }

    private fun listenForFirstProducerEmission() {
        disposables += SomeProducer.producerEvents
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .singleOrError()
            .subscribeBy(
                onSuccess = {
                    startActivity(MainActivity.buildIntent(this))
                }
            )
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}
