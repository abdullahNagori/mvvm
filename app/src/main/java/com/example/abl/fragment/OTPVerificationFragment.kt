package com.example.abl.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import com.example.abl.R
import com.example.abl.activity.LoginActivity
import com.example.abl.activity.MainActivity
import com.example.abl.activity.WelcomeActivity
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.OtpVerificationFragmentBinding
import com.example.abl.fragment.CalculatorFragment.Companion.newInstance
import com.example.abl.model.DynamicLeadsItem
import com.example.abl.model.OtpModel
import com.example.abl.model.OtpResponse
import com.example.abl.utils.GsonFactory


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

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun setTitle(text: String) {
        TODO("Not yet implemented")
    }

    override fun <T> initiateListArrayAdapter(list: List<T>): ArrayAdapter<T> {
        TODO("Not yet implemented")
    }
}