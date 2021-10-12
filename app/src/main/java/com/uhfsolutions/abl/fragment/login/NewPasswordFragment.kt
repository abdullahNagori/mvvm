package com.uhfsolutions.abl.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.activity.WelcomeActivity
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.databinding.FragmentNewPasswordBinding
import com.uhfsolutions.abl.model.changePassword.VerifyPassModel


class NewPasswordFragment : BaseDockFragment() {

    lateinit var binding: FragmentNewPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this
        return binding.root
    }

    private fun initView(){
        binding = FragmentNewPasswordBinding.inflate(layoutInflater)
        binding.btnChngPass.setOnClickListener {
            if (binding.edNewPassword.text.toString() == "") {
                myDockActivity?.showErrorMessage(getString(R.string.error_empty_pass))
            } else {
                verifyPwdReq(binding.edNewPassword.text.toString())
            }
        }
    }

    private fun verifyPwdReq(pass: String){
        myDockActivity?.getUserViewModel()?.verifyPwdReq(VerifyPassModel(pass))
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.VERIFY_PWD_REQ -> {
                // Redirect to welcome screen
                startActivity(Intent(requireContext(), WelcomeActivity::class.java))
            }
        }
    }
}