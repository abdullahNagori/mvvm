package com.example.abl.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.activity.LoginActivity
import com.example.abl.base.BaseFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.OtpVerificationFragmentBinding


class OTPVerificationFragment : BaseFragment() {

    lateinit var binding: OtpVerificationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initView()

        binding.btnVerify.setOnClickListener { onClick() }
        return binding.root
    }

    private fun initView() {
        binding = OtpVerificationFragmentBinding.inflate(layoutInflater)
    }

    private fun onClick() {
        when {
            TextUtils.isEmpty(binding.edOTP1.text.toString()) -> {
                showBanner(getString(R.string.enter_otp), Constants.ERROR)
            }
            TextUtils.isEmpty(binding.edOTP2.text.toString()) -> {
                showBanner(getString(R.string.enter_otp), Constants.ERROR)
            }
            TextUtils.isEmpty(binding.edOTP3.text.toString()) -> {
                showBanner(getString(R.string.enter_otp), Constants.ERROR)
            }
            TextUtils.isEmpty(binding.edOTP4.text.toString()) -> {
                showBanner(getString(R.string.enter_otp), Constants.ERROR)
            }
            TextUtils.isEmpty(binding.edOTP5.text.toString()) -> {
                showBanner(getString(R.string.enter_otp), Constants.ERROR)
            }

            else -> {
                LoginActivity.navController.navigate(R.id.action_OTPVerificationFragment_to_newPasswordFragment)
            }
        }
    }
}