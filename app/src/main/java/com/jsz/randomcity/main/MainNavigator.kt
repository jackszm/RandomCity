package com.jsz.randomcity.main

import androidx.annotation.ColorRes
import com.jsz.randomcity.details.DetailsActivity

class MainNavigator(private val activity: MainActivity) {

    fun toDetails(
        name: String,
        @ColorRes color: Int,
        latitude: Double,
        longitude: Double
    ) {
        activity.startActivity(
            DetailsActivity.buildIntent(activity, name, color, latitude, longitude)
        )
    }
}
