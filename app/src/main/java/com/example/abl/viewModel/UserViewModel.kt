package com.example.abl.viewModel

import androidx.lifecycle.ViewModel
import com.example.abl.model.LoginModel
import com.example.abl.model.OtpModel
import com.example.abl.model.ResetPasswordModel
import com.example.abl.model.VerifyPassModel
import com.example.abl.network.ApiListener
import com.example.abl.repository.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    var apiListener: ApiListener? = null

    fun login(loginModel: LoginModel){
        userRepository.apiListener = apiListener
        userRepository.login(loginModel)
    }

    fun verifyOtp(otpModel: OtpModel){
        userRepository.apiListener = apiListener
        userRepository.verifyOpt(otpModel)
    }

    fun resetPwdReq(resetPasswordModel: ResetPasswordModel){
        userRepository.apiListener = apiListener
        userRepository.resetPasswordReq(resetPasswordModel)
    }

    fun verifyPwdReq(verifyPassModel: VerifyPassModel, token: String){
        userRepository.apiListener = apiListener
        userRepository.verifyPasswordReq(verifyPassModel, token)
    }
}