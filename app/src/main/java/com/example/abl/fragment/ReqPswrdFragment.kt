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
import com.example.abl.databinding.ReqPasswordFragmentBinding
import com.example.abl.model.OtpModel
import com.example.abl.model.OtpResponse
import com.example.abl.model.ResetPasswordModel
import com.example.abl.model.ResetPwdReqResponse
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

    private fun initView(){

        binding = ReqPasswordFragmentBinding.inflate(layoutInflater)

        binding.btnChngPass.setOnClickListener {
            if (binding.edNewPassword.text.toString() == "")
            {
                showBanner(getString(R.string.user_name), Constants.ERROR)
            }
            else
            {
                resetPwdReq(binding.edNewPassword.text.toString())
            }
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