package com.example.utsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.utsapp.R
import com.example.utsapp.model.Weather

class WeatherAdapter(
	private val items : List<Weather>, private val onItemClick : (Weather) -> Unit
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

	class WeatherViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
		val image : LottieAnimationView? = itemView.findViewById(R.id.WeatherAnimation)
		val location : TextView? = itemView.findViewById(R.id.WeatherLocation)
		val temperature : TextView? = itemView.findViewById(R.id.WeatherTemperature)
	}

	override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : WeatherViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
		return WeatherViewHolder(view)
	}

	override fun onBindViewHolder(holder : WeatherViewHolder, position : Int) {
		val weather = items[position]

		holder.image?.setAnimation(weather.weatherImage)
		holder.image?.playAnimation()
		holder.location?.text = weather.location
		holder.temperature?.text = weather.temperature

		// Saat item diklik → tampilkan data
		holder.itemView.setOnClickListener {
			onItemClick(weather)
		}
	}

	override fun getItemCount() : Int = items.size
}
