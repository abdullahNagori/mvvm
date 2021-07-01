package com.example.abl.fragment

import android.os.Bundle
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
import com.example.abl.databinding.FragmentForgotPasswordBinding
import com.example.abl.model.ResetPasswordModel
import com.example.abl.model.ResetPwdReqResponse
import com.example.abl.utils.GsonFactory


class ForgotPasswordFragment : BaseDockFragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this
        binding.btnGenOtp.setOnClickListener { onCLickEvent()}
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
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
    }

    private fun onCLickEvent() {
        if (binding.edForgotUserName.text.toString() == "")
        {
            showBanner(getString(R.string.user_name), Constants.ERROR)
        }
        else {
           // LoginActivity.navController.navigate(R.id.action_forgotPassFragment_to_OTPVerificationFragment)
            resetPwdReq(binding.edForgotUserName.text.toString())
        }

    }

    private fun resetPwdReq(loginID: String){
        myDockActivity?.getUserViewModel()?.resetPwdReq(ResetPasswordModel(loginID))
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.RESET_PWD_REQ -> {
                try
                {
                    Log.d("liveDataValue", liveData.value.toString())
                    val resetReqResponseEnt = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, ResetPwdReqResponse::class.java)

                    if (resetReqResponseEnt?.message == "OTP send to registered Number")
                    {
                        myDockActivity?.showSuccessMessage(resetReqResponseEnt.message.toString())
                        LoginActivity.navController.navigate(R.id.action_reqPasswordFragment_to_newPassFragment)
                    }
                    else{
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