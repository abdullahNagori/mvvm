package com.example.abl.fragment

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.activity.LoginActivity
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.FragmentLoginBinding
import com.example.abl.model.LoginModel
import com.example.abl.model.LoginResponse
import com.example.abl.utils.GsonFactory

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
                showBanner(getString(R.string.error_empty_email), Constants.ERROR)
            }
            isEmpty(binding.edPassword.text.toString()) -> {
                showBanner(getString(R.string.error_empty_pass), Constants.ERROR)
            }

            else -> {

                loginUser()

            }
        }
    }

    private fun loginUser() {

        email = binding.edUserName.text.toString()
        password = binding.edPassword.text.toString()


        myDockActivity?.getUserViewModel()
            ?.login(LoginModel(email, password))
        arguments?.getString(email)
        sharedPrefManager.storeUserId(email)
        Log.i("xxLoginID", arguments?.getString(email).toString())
    }


    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.LOGIN -> {
                try {
                    Log.d("liveDataValue", liveData.value.toString())
                    val routeResponseEnt = GsonFactory.getConfiguredGson()
                        ?.fromJson(liveData.value, LoginResponse::class.java)

                    if (routeResponseEnt?.two_factor == "yes") {
                        myDockActivity?.showSuccessMessage(getString(R.string.success))
                        LoginActivity.navController.navigate(R.id.action_loginFragment_to_otpFragment)
                    }
//                    else{
//                        if (routeResponseEnt?.token != null && routeResponseEnt.token!!.isNotEmpty())
//                        {
//                            //token saved into shared pref and navigate into welcome
//                            val welcomeIntent = Intent(context, WelcomeActivity::class.java)
//                            welcomeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                            startActivity(welcomeIntent)
//                            activity?.finish()
//                            activity?.overridePendingTransition(R.anim.bottomtotop, R.anim.toptobottom)
//                        }

                    else {
                        myDockActivity?.showErrorMessage(getString(R.string.something_went_wrong))
                    }

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }


            }

        }

    }

    override fun onFailure(message: String, tag: String) {
        super.onFailure(message, tag)
        if (tag == Constants.LOGIN) {
            Log.i("xxError", "Error")
        }
    }
}