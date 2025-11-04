package com.example.utsapp.model

data class Weather(
	val weatherImage : Int,
	val day : String,
	val temperature : String,
	val location : String,
	val condition : String,
	val date : String,
	val windSpeed : String,
	val rainChance : String,
	val humidity : String
)

