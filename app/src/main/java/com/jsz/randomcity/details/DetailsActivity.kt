package com.jsz.randomcity.details

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.jsz.randomcity.R

class DetailsActivity : AppCompatActivity(), OnMapReadyCallback {


    private fun name() = intent.getStringExtra(EXTRA_NAME)!!

    @ColorRes
    private fun toolbarColor() = intent.getIntExtra(EXTRA__COLOR, 0)
    private fun latitude() = intent.getDoubleExtra(EXTRA_LATITUDE, 0.0)
    private fun longitude() = intent.getDoubleExtra(EXTRA_LONGITUDE, 0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        if (toolbarColor() == R.color.white) {
            setTheme(R.style.AppTheme_LightActionBar);
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.apply {
            title = name()
            setBackgroundDrawable(ColorDrawable(resources.getColor(toolbarColor())))
        }


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val city = LatLng(latitude(), longitude())
        googleMap.animateCamera(
            CameraUpdateFactory.zoomTo(12.0f),
            object : GoogleMap.CancelableCallback {
                override fun onFinish() {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(city))
                }

                override fun onCancel() {
                    // do Nothing
                }

            }
        );

    }

    companion object {

        const val EXTRA_NAME = "EXTRA_NAME"
        const val EXTRA__COLOR = "EXTRA__COLOR"
        const val EXTRA_LATITUDE = "EXTRA_LATITUDE"
        const val EXTRA_LONGITUDE = "EXTRA_LONGITUDE"

        fun buildIntent(
            context: Context,
            name: String,
            @ColorRes color: Int,
            latitude: Double,
            longitude: Double
        ) = Intent(context, DetailsActivity::class.java).apply {
            putExtra(EXTRA_NAME, name)
            putExtra(EXTRA__COLOR, color)
            putExtra(EXTRA_LATITUDE, latitude)
            putExtra(EXTRA_LONGITUDE, longitude)
        }

    }
}
