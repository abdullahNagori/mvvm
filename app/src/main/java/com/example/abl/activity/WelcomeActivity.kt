package com.example.abl.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import com.example.abl.R
import com.example.abl.base.BaseActivity
import com.example.abl.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        logoAnimation()
        fabAnimation()

        binding.fab.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    private fun logoAnimation(){
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            binding.imgLogo.visibility = View.VISIBLE
            val anim = AnimationUtils.loadAnimation(this, R.anim.toptobottom)
            binding.imgLogo.startAnimation(anim)
        }, 700)
    }

    private fun fabAnimation() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            binding.fab.visibility = View.VISIBLE
            val anim = AnimationUtils.loadAnimation(this, R.anim.righttoleft)
            binding.fab.startAnimation(anim)
        }, 700)
    }
}