package com.uhfsolutions.abl.fragment.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.activity.LoginActivity
import com.uhfsolutions.abl.activity.WelcomeActivity
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.databinding.OtpVerificationFragmentBinding
import com.uhfsolutions.abl.model.otp.OtpModel
import com.uhfsolutions.abl.model.otp.OtpResponse
import com.uhfsolutions.abl.utils.GsonFactory


class OTPVerificationFragment : BaseDockFragment() {

    lateinit var binding: OtpVerificationFragmentBinding
    lateinit var loginID: String
    private var isPinnFilled = false
    private lateinit var pin: String
    var isResetPassword = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this
        loginID = arguments?.getString("LOGIN_ID", "" ).toString()
        isResetPassword = arguments?.getBoolean("RESET_PASSWORD") == true
        binding.btnVerify.setOnClickListener { onVerifyClickEvent() }
        return binding.root
    }

    private fun initView() {
        binding = OtpVerificationFragmentBinding.inflate(layoutInflater)
        setPinViewListener()
    }

    private fun setPinViewListener() {
        binding.pinView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().length == 6) {
                    isPinnFilled = true
                    pin = editable.toString()
                    myDockActivity?.hideKeyboard(binding.pinView)
                }
            }
        })
    }

    private fun onVerifyClickEvent() {
        if (isPinnFilled) {
            verifyOTP(pin)
        }
    }

    private fun verifyOTP(otp: String){
        myDockActivity?.showProgressIndicator()
        myDockActivity?.getUserViewModel()?.verifyOtp(OtpModel(loginID,otp))
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.VERIFY_OTP -> {
                try {
                    val otpResponseEnt = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, OtpResponse::class.java)
                        if (otpResponseEnt?.verify == "yes") {
                            if (!otpResponseEnt.token.isNullOrBlank()) {
                                sharedPrefManager.setToken(otpResponseEnt.token.toString())
                                if (isResetPassword) {
                                    LoginActivity.navController.navigate(R.id.action_OTPVerificationFragment_to_newPassFragment)
                                } else {
                                    startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                                    activity?.finish()
                                }
                            } else {
                                myDockActivity?.showErrorMessage(getString(R.string.something_went_wrong))
                            }
                        } else {
                            myDockActivity?.showErrorMessage(getString(R.string.verification_failed))
                        }
                }
                catch (e: Exception){
                    myDockActivity?.showErrorMessage(getString(R.string.something_went_wrong))
                }
            }
        }
    }






}