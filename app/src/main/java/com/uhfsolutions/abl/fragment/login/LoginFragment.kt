package com.uhfsolutions.abl.fragment.login

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.activity.LoginActivity
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.databinding.FragmentLoginBinding
import com.uhfsolutions.abl.model.login.LoginModel
import com.uhfsolutions.abl.model.login.LoginResponse
import com.uhfsolutions.abl.utils.GsonFactory

class LoginFragment : BaseDockFragment() {
    private lateinit var binding: FragmentLoginBinding

    lateinit var email: String
    lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initView()
        binding.txtForgotPassword.setOnClickListener(::onCLickEvent)
        binding.btnLogin.setOnClickListener(::onCLickEvent)
        myDockActivity?.getUserViewModel()?.apiListener = this

        return binding.root
    }

    private fun initView() {
        binding = FragmentLoginBinding.inflate(layoutInflater)
    }

    private fun onCLickEvent(view: View) {
        when (view.id) {
            R.id.txtForgotPassword -> LoginActivity.navController.navigate(R.id.action_loginFragment_to_forgotPassFragment)
            R.id.btnLogin -> auth()
        }
    }

    private fun auth() {
        when {
            isEmpty(binding.edUserName.text.toString()) -> {
                myDockActivity?.showErrorMessage(getString(R.string.error_empty_email))
            }
            isEmpty(binding.edPassword.text.toString()) -> {
                myDockActivity?.showErrorMessage(getString(R.string.error_empty_pass))
            }

            else -> {

                loginUser()

            }
        }
    }

    private fun loginUser() {
        email = binding.edUserName.text.toString()
        password = binding.edPassword.text.toString()
        myDockActivity?.showProgressIndicator()
        myDockActivity?.getUserViewModel()?.login(LoginModel(email, password))
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        myDockActivity?.hideProgressIndicator()
        when (tag) {
            Constants.LOGIN -> {
                try {
                    val loginResponseEnt = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, LoginResponse::class.java)
                    if (loginResponseEnt?.two_factor == "yes") {
                        val bundle = Bundle()
                        bundle.putString("LOGIN_ID", binding.edUserName.text.toString())
                        sharedPrefManager.setUsername(binding.edUserName.text.toString())
                        LoginActivity.navController.navigate(R.id.action_loginFragment_to_otpFragment, bundle)

                    } else {
                        if (loginResponseEnt?.token != null && (loginResponseEnt?.token)!!.isNotEmpty()) {
                            sharedPrefManager.setToken(loginResponseEnt.token.toString())
                            val bundle = Bundle()
                            bundle.putString(Constants.LOGIN_TWO_FACTOR, "no")
                            LoginActivity.navController.navigate(R.id.action_loginFragment_to_welcomeFragment,bundle)
                        } else {
                            myDockActivity?.showErrorMessage(getString(R.string.something_went_wrong))
                        }
                    }
                } catch (e: Exception) {
                    myDockActivity?.showErrorMessage(getString(R.string.something_went_wrong))
                }
            }
        }
    }

    override fun onFailure(message: String, tag: String) {
        super.onFailure(message, tag)
        myDockActivity?.hideProgressIndicator()
        if (tag == Constants.LOGIN) {
            Log.i("xxError", "Error")
        }
    }

//    override fun closeDrawer() {
//        TODO("Not yet implemented")
//    }
//
//    override fun navigateToFragment(id: Int, args: Bundle?) {
//        TODO("Not yet implemented")
//    }
//
//    override fun setTitle(text: String) {
//        TODO("Not yet implemented")
//    }
//
//    override fun <T> initiateListArrayAdapter(list: List<T>): ArrayAdapter<T> {
//        TODO("Not yet implemented")
//    }
}