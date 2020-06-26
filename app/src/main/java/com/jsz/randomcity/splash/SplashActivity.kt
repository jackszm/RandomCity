package com.jsz.randomcity.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.jsz.randomcity.AppNavigator

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels {
        SplashViewModelFactory(
            navigator = AppNavigator(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.observe(this) {
            // do Nothing - no state to observe
        }
    }
}
