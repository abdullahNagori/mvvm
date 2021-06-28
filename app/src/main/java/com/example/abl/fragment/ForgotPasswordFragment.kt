package com.example.abl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.activity.LoginActivity
import com.example.abl.base.BaseFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.FragmentForgotPasswordBinding


class ForgotPasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()

        binding.btnGenOtp.setOnClickListener { onCLickEvent()}
        return binding.root
    }

    private fun initView() {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
    }

    private fun onCLickEvent() {
        if (binding.edForgotUserName.text.toString() == "")
        {
            showBanner(getString(R.string.user_name), Constants.ERROR)
        }
        else {
            LoginActivity.navController.navigate(R.id.action_forgotPassFragment_to_OTPVerificationFragment)
        }
    }
}