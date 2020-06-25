package com.jsz.randomcity.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jsz.randomcity.R
import com.jsz.randomcity.main.MainViewModel.City

class CitiesAdapter(
    private val clickAction: (city: City) -> Unit = {}
) : ListAdapter<City, CitiesAdapter.MyViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return MyViewHolder(clickAction, view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MyViewHolder(
        private val clickAction: (city: City) -> Unit,
        view: View
    ) : RecyclerView.ViewHolder(view) {
        private val nameTextView = view.findViewById<TextView>(R.id.cityName)
        private val timeStampTextView = view.findViewById<TextView>(R.id.cityTimestamp)

        fun bind(city: City) {
            itemView.setOnClickListener { clickAction(city) }
            nameTextView.text = city.name
            nameTextView.setTextColor(nameTextView.resources.getColor(city.color))
            timeStampTextView.text = city.timeStamp
        }
    }
}

private val diffCallback = object : ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.timeStamp == newItem.timeStamp
    }

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }

}
