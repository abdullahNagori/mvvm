package com.example.abl.viewModel

import androidx.lifecycle.ViewModel
import com.example.abl.fragment.CheckinModel
import com.example.abl.model.*
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

    fun uerDetails(token: String){
        userRepository.apiListener = apiListener
        userRepository.getUserDetails(token)
    }

    fun markAttendance(markAttendanceModel: MarkAttendanceModel, token: String){
        userRepository.apiListener = apiListener
        userRepository.markAttendance(markAttendanceModel, token)
    }

    fun getLovs(){
        userRepository.apiListener = apiListener
        userRepository.getLovs()
    }

    fun getDynamicLeads(token: String){
        userRepository.apiListener = apiListener
        userRepository.getDynamicLeads(token)
    }

    fun addLead(customerDetail: CustomerDetail){
        userRepository.apiListener = apiListener
        userRepository.addLead(customerDetail)
    }

    fun addLeadCheckin(checkinModel: CheckinModel){
        userRepository.apiListener = apiListener
        userRepository.addLeadCheckin(checkinModel)
    }

    fun getDashBoard() {
        userRepository.apiListener = apiListener
        userRepository.getDashboard()
    }
}