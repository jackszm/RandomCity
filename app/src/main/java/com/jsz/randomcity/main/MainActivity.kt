package com.jsz.randomcity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.jsz.randomcity.R
import com.jsz.randomcity.RandomCityApp
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val cityStorage by lazy { (application as RandomCityApp).db.cityStorage() }
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(cityStorage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = CitiesAdapter { viewModel.onCityClicked(it) }
        recycler_view.apply {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.observeCities().observe(this, Observer<List<MainViewModel.City>> { cities ->
            adapter.submitList(cities)
        })
    }

    companion object {

        fun buildIntent(context: Context) = Intent(context, MainActivity::class.java)

    }
}
