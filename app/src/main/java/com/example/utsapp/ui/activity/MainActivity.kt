package com.example.utsapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.utsapp.R
import com.example.utsapp.ui.fragment.ArticleFragment
import com.example.utsapp.ui.fragment.BiodataFragment
import com.example.utsapp.ui.fragment.CalculatorFragment
import com.example.utsapp.ui.fragment.ContactFragment
import com.example.utsapp.ui.fragment.WeatherFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

		// Tampilkan fragment pertama (default)
		loadFragment(BiodataFragment())

		bottomNav.setOnItemSelectedListener { item ->
			when (item.itemId) {
				R.id.nav_biodata -> loadFragment(BiodataFragment())
				R.id.nav_contact -> loadFragment(ContactFragment())
				R.id.nav_calculator -> loadFragment(CalculatorFragment())
				R.id.nav_weather -> loadFragment(WeatherFragment())
				R.id.nav_news -> loadFragment(ArticleFragment())
			}
			true
		}
	}

	private fun loadFragment(fragment : Fragment) {
		supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
	}
}