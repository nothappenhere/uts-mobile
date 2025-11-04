package com.example.utsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.utsapp.R
import com.example.utsapp.adapter.WeatherAdapter
import com.example.utsapp.model.Weather
import org.json.JSONArray

class WeatherFragment : Fragment() {

	private lateinit var adapter : WeatherAdapter

	override fun onCreateView(
		inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?
	) : View? {
		val view = inflater.inflate(R.layout.weather_layout, container, false)

		// Setup RecyclerView
		val recyclerView = view.findViewById<RecyclerView>(R.id.weatherRecycler)
		recyclerView.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

		// Ambil referensi elemen tampilan utama
		val locationText = view.findViewById<TextView>(R.id.weatherLocation)
		val animationView = view.findViewById<LottieAnimationView>(R.id.weatherAnimation)
		val temperatureText = view.findViewById<TextView>(R.id.weatherTemperature)
		val conditionText = view.findViewById<TextView>(R.id.weatherCondition)
		val dateText = view.findViewById<TextView>(R.id.weatherDate)
		val windSpeedText = view.findViewById<TextView>(R.id.windSpeedText)
		val rainChanceText = view.findViewById<TextView>(R.id.rainChanceText)
		val humidityText = view.findViewById<TextView>(R.id.humidityText)

		val listWeather = loadFromAssets()

		// Adapter dengan callback klik → Update UI utama berdasarkan data item yang diklik
		adapter = WeatherAdapter(listWeather) { selectedWeather ->
			locationText.text = selectedWeather.location
			animationView.setAnimation(selectedWeather.weatherImage)
			animationView.playAnimation()
			temperatureText.text = selectedWeather.temperature
			conditionText.text = selectedWeather.condition
			dateText.text = selectedWeather.date
			windSpeedText.text = selectedWeather.windSpeed
			rainChanceText.text = selectedWeather.rainChance
			humidityText.text = selectedWeather.humidity
		}
		recyclerView.adapter = adapter

		// Set default tampilan item pertama
		listWeather.firstOrNull()?.let { defaultWeather ->
			locationText.text = defaultWeather.location
			animationView.setAnimation(defaultWeather.weatherImage)
			animationView.playAnimation()
			temperatureText.text = defaultWeather.temperature
			conditionText.text = defaultWeather.condition
			dateText.text = defaultWeather.date
			windSpeedText.text = defaultWeather.windSpeed
			rainChanceText.text = defaultWeather.rainChance
			humidityText.text = defaultWeather.humidity
		}

		return view
	}

	private fun loadFromAssets() : List<Weather> {
		val jsonString =
			requireContext().assets.open("weathers.json").bufferedReader().use { it.readText() }

		// Gunakan org.json untuk parsing sederhana
		val jsonArray = JSONArray(jsonString)
		val weatherList = mutableListOf<Weather>()

		for (i in 0 until jsonArray.length()) {
			val item = jsonArray.getJSONObject(i)

			val imageName = item.getString("weatherImage")
			val imageResId = resources.getIdentifier(imageName, "raw", requireContext().packageName)

			weatherList.add(
				Weather(
					imageResId,
					item.getString("day"),
					item.getString("temperature"),
					item.getString("location"),
					item.getString("condition"),
					item.getString("date"),
					item.getString("windSpeed"),
					item.getString("rainChance"),
					item.getString("humidity")
				)
			)
		}

		return weatherList
	}
}