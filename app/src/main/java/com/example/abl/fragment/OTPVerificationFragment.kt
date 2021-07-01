package com.example.abl.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.activity.LoginActivity
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.BaseFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.OtpVerificationFragmentBinding
import com.example.abl.model.LoginModel
import com.example.abl.model.LoginResponse
import com.example.abl.model.OtpModel
import com.example.abl.model.OtpResponse
import com.example.abl.utils.GsonFactory


class OTPVerificationFragment : BaseDockFragment() {

    lateinit var binding: OtpVerificationFragmentBinding
    lateinit var loginID: String
    private var isPinnFilled = false
    private lateinit var pin: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this
        binding.btnVerify.setOnClickListener { onClick() }
        return binding.root
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

    private fun initView() {
        binding = OtpVerificationFragmentBinding.inflate(layoutInflater)
        loginID =  sharedPrefManager.getUserId()
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
    private fun onClick() {

        if (isPinnFilled)
        {
            verifyOTP(pin)
        }

    }

    private fun verifyOTP(otp: String){
        myDockActivity?.getUserViewModel()?.verifyOtp(OtpModel(loginID,otp))
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.VERIFY_OTP -> {
                try
                {
                    Log.d("liveDataValue", liveData.value.toString())
                    val otpResponseEnt = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, OtpResponse::class.java)

                    if (otpResponseEnt?.verify == "yes")
                    {
                        myDockActivity?.showSuccessMessage(getString(R.string.success))
                        sharedPrefManager.setToken(otpResponseEnt.token.toString())
                        LoginActivity.navController.navigate(R.id.action_OTPVerificationFragment_to_newPasswordFragment)

                    }
                    else{
                        if (otpResponseEnt?.token != null && otpResponseEnt.token!!.isNotEmpty())
                        {
                            myDockActivity?.showErrorMessage(getString(R.string.token_null))

                        }
                        else
                            myDockActivity?.showErrorMessage(getString(R.string.something_went_wrong))
                    }
                }
                catch (e: Exception){
                    Log.d("Exception",e.message.toString())
                }
            }
        }
    }
}