package com.jsz.randomcity.details;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.jsz.randomcity.R;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String EXTRA_NAME = "EXTRA_NAME";
    private static final String EXTRA_COLOR = "EXTRA__COLOR";
    private static final String EXTRA_LATITUDE = "EXTRA_LATITUDE";
    private static final String EXTRA_LONGITUDE = "EXTRA_LONGITUDE";

    public static Intent buildIntent(
            Context context,
            String name,
            @ColorRes int color,
            double latitude,
            double longitude
    ) {
        return new Intent(context, DetailsActivity.class)
                .putExtra(EXTRA_NAME, name)
                .putExtra(EXTRA_COLOR, color)
                .putExtra(EXTRA_LATITUDE, latitude)
                .putExtra(EXTRA_LONGITUDE, longitude);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getIntent().getIntExtra(EXTRA_COLOR, 0) == R.color.white) {
            setTheme(R.style.AppTheme_LightActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setupToolbar();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void setupToolbar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(getIntent().getStringExtra(EXTRA_NAME));

            int colorResId = getIntent().getIntExtra(EXTRA_COLOR, 0);
            ColorDrawable backgroundColor = new ColorDrawable(getResources().getColor(colorResId));
            supportActionBar.setBackgroundDrawable(backgroundColor);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng cityCoordinates = new LatLng(
                getIntent().getDoubleExtra(EXTRA_LATITUDE, 0),
                getIntent().getDoubleExtra(EXTRA_LONGITUDE, 0)
        );

        googleMap.animateCamera(
                CameraUpdateFactory.zoomTo(12.0f),
                moveCameraOnZoomFinished(googleMap, cityCoordinates)
        );
    }

    private GoogleMap.CancelableCallback moveCameraOnZoomFinished(
            GoogleMap googleMap,
            LatLng cityCoordinates
    ) {
        return new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(cityCoordinates));
            }

            @Override
            public void onCancel() {
                // Do Nothing
            }
        };
    }

}
