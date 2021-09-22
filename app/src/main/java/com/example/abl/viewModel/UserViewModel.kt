package com.example.abl.viewModel

import androidx.lifecycle.ViewModel
import com.example.abl.model.changePassword.ChangePasswordModel
import com.example.abl.model.changePassword.VerifyPassModel
import com.example.abl.model.login.LoginModel
import com.example.abl.model.markAttendance.MarkAttendanceModel
import com.example.abl.model.otp.OtpModel
import com.example.abl.model.resetPassword.ResetPasswordModel
import com.example.abl.model.trainingAndQuiz.GetQuizModel
import com.example.abl.model.trainingAndQuiz.SubmitQuizModel
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


    fun getMarketingCollateral(){
        userRepository.apiListener = apiListener
        userRepository.getMarketingCollateral()
    }

    fun getDashBoard() {
        userRepository.apiListener = apiListener
        userRepository.getDashboard()
    }

    fun changePassword(changePasswordModel: ChangePasswordModel) {
        userRepository.apiListener = apiListener
        userRepository.changePassword(changePasswordModel)
    }

    fun getTrainings() {
        userRepository.apiListener = apiListener
        userRepository.getTrainings()
    }

    fun getQuiz(getQuizModel: GetQuizModel) {
        userRepository.apiListener = apiListener
        userRepository.getQuizes(getQuizModel)
    }

    fun submitQuiz(submitQuizModel: SubmitQuizModel) {
        userRepository.apiListener = apiListener
        userRepository.submitQuiz(submitQuizModel)
    }
}