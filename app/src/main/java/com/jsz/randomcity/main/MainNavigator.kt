package com.jsz.randomcity.main

import com.jsz.randomcity.details.DetailsActivity

class MainNavigator(private val activity: MainActivity) {

    fun toDetails() {
        activity.startActivity(DetailsActivity.buildIntent(activity))
    }
}
