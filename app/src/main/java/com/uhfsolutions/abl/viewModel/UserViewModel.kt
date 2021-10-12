package com.uhfsolutions.abl.viewModel

import androidx.lifecycle.ViewModel
import com.uhfsolutions.abl.model.changePassword.ChangePasswordModel
import com.uhfsolutions.abl.model.changePassword.VerifyPassModel
import com.uhfsolutions.abl.model.login.LoginModel
import com.uhfsolutions.abl.model.markAttendance.MarkAttendanceModel
import com.uhfsolutions.abl.model.otp.OtpModel
import com.uhfsolutions.abl.model.resetPassword.ResetPasswordModel
import com.uhfsolutions.abl.network.ApiListener
import com.uhfsolutions.abl.repository.UserRepository
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

    fun verifyPwdReq(verifyPassModel: VerifyPassModel){
        userRepository.apiListener = apiListener
        userRepository.verifyPasswordReq(verifyPassModel)
    }

    fun uerDetails(token: String){
        userRepository.apiListener = apiListener
        userRepository.getUserDetails(token)
    }

    fun markAttendance(markAttendanceModel: MarkAttendanceModel, token: String){
        userRepository.apiListener = apiListener
        userRepository.markAttendance(markAttendanceModel, token)
    }

    fun changePassword(changePasswordModel: ChangePasswordModel) {
        userRepository.apiListener = apiListener
        userRepository.changePassword(changePasswordModel)
    }

}