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

//        val isFirstRun = SharedPrefManager.get(Constant.FIRST_TIME) as Boolean?
//        if (isFirstRun == null) {
//            SharedPrefManager.put(false, Constant.FIRST_TIME)
//        }
    }

    private fun logoAnimation() {
//        val anim = AnimationUtils.loadAnimation(this,R.anim.toptobottom)
//        binding.imgLogo.startAnimation(anim)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            binding.imgLogo.visibility = View.GONE
            nextView()
        }, TIME_OUT)
//        binding.imgLogo.startAnimation(
//            AnimationUtils.loadAnimation(
//                this@SplashActivity,
//            )
//        )
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