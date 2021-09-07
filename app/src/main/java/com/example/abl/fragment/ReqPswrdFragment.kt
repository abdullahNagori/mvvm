package com.example.abl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.activity.LoginActivity
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.ReqPasswordFragmentBinding
import com.example.abl.model.resetPassword.ResetPasswordModel
import com.example.abl.model.resetPassword.ResetPwdReqResponse
import com.example.abl.utils.GsonFactory


class ReqPswrdFragment : BaseDockFragment() {

    lateinit var binding: ReqPasswordFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this
       return binding.root
    }

    private fun initView(){
        binding = ReqPasswordFragmentBinding.inflate(layoutInflater)
        binding.btnChngPass.setOnClickListener { onResetPasswordClickEvent() }
    }

    private fun onResetPasswordClickEvent() {
        if (binding.edNewPassword.text.toString().isEmpty())  {
            myDockActivity?.showErrorMessage(getString(R.string.error_login_id))
            return
        }

        myDockActivity?.showProgressIndicator()
        myDockActivity?.getUserViewModel()?.resetPwdReq(ResetPasswordModel(binding.edNewPassword.text.toString()))
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        myDockActivity?.hideProgressIndicator()
        when (tag) {
            Constants.RESET_PWD_REQ -> {
                try {
                    val resetReqResponseEnt = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, ResetPwdReqResponse::class.java)
                    myDockActivity?.showSuccessMessage(resetReqResponseEnt?.message.toString())
                    LoginActivity.navController.navigate(R.id.action_reqPasswordFragment_to_newPassFragment)

//                    if (resetReqResponseEnt?.message == "OTP send to registered Number") {
//                        myDockActivity?.showSuccessMessage(resetReqResponseEnt.message.toString())
//                        LoginActivity.navController.navigate(R.id.action_reqPasswordFragment_to_newPassFragment)
//                    } else{
//                        myDockActivity?.showErrorMessage(getString(R.string.something_went_wrong))
//                    }
                }
                catch (e: Exception){
                    Log.d("Exception",e.message.toString())
                }
            }
        }
    }

    override fun onFailure(message: String, tag: String) {
        super.onFailure(message, tag)
        myDockActivity?.hideProgressIndicator()
    }






}