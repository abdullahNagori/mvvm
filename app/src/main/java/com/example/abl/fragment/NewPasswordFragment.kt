package com.example.abl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.activity.LoginActivity
import com.example.abl.base.BaseFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.NewPasswordFragmentBinding


class NewPasswordFragment : BaseFragment() {

    lateinit var binding: NewPasswordFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initView()

       return binding.root
    }

    private fun initView(){

        binding = NewPasswordFragmentBinding.inflate(layoutInflater)

        binding.btnChngPass.setOnClickListener {
            if (binding.edNewPassword.text.toString() == "")
            {
                showBanner(getString(R.string.enter_new_password), Constants.ERROR)
            }
            else
            {
                LoginActivity.navController.navigate(R.id.action_newPasswordFragment_to_loginFragment)
            }
        }

    }

}