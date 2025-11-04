package com.example.utsapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.utsapp.R
import com.example.utsapp.ui.activity.MainActivity

class SplashActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.splash_layout)

		// Jalankan selama 5 detik, lalu pindah ke MainActivity
		Handler(Looper.getMainLooper()).postDelayed({
			val intent = Intent(this@SplashActivity, MainActivity::class.java)
			startActivity(intent)
			finish()
		}, 5000)
	}
}