package com.example.abl.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        logoAnimation()
        fabAnimation()

        binding.fab.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
        }

        return binding.root
    }

    private fun initView(){
        binding = FragmentWelcomeBinding.inflate(layoutInflater)
    }

    private fun logoAnimation(){
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            binding.imgLogo.visibility = View.VISIBLE
            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.toptobottom)
            binding.imgLogo.startAnimation(anim)
        }, 700)
    }

    private fun fabAnimation() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            binding.fab.visibility = View.VISIBLE
            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.righttoleft)
            binding.fab.startAnimation(anim)
        }, 700)
    }

}