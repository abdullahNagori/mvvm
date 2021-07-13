package com.example.abl.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.activity.LoginActivity
import com.example.abl.activity.WelcomeActivity
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.FragmentNewPasswordBinding
import com.example.abl.model.ResetPasswordModel
import com.example.abl.model.ResetPwdReqResponse
import com.example.abl.model.VerifyPassModel
import com.example.abl.model.VerifyPwdReqResponse
import com.example.abl.utils.GsonFactory


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
        binding = FragmentNewPasswordBinding.inflate(layoutInflater)
        binding.btnChngPass.setOnClickListener {
            if (binding.edNewPassword.text.toString() == "") {
                showBanner(getString(R.string.error_empty_pass), Constants.ERROR)
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