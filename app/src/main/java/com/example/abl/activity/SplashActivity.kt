package com.example.abl.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.abl.R
import com.example.abl.databinding.ActivitySplashBinding
import com.example.abl.utils.SharedPrefManager

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val TIME_OUT: Long = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        logoAnimation()
    }

    private fun logoAnimation() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            binding.imgLogo.visibility = View.GONE
            nextView()
        }, TIME_OUT)
    }


    private fun nextView() {

        val sharedPrefManager = SharedPrefManager(this)

        if (sharedPrefManager.getToken().isNotEmpty()) {
            if (sharedPrefManager.getShiftStart()){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }
}