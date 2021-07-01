package com.example.abl.repository

import androidx.lifecycle.MutableLiveData
import com.example.abl.constant.Constants
import com.example.abl.model.*
import com.example.abl.network.Api
import com.example.abl.utils.SharedPrefManager
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: Api, private val sharedPrefManager: SharedPrefManager):BaseRepository()
{
    fun login(loginModel: LoginModel): MutableLiveData<String> {
        return callApi(api.login(loginModel), Constants.LOGIN)
    }

    fun verifyOpt(otpModel: OtpModel): MutableLiveData<String> {
        return callApi(api.verifyOtp(otpModel), Constants.VERIFY_OTP)
    }

    fun resetPasswordReq(resetPasswordModel: ResetPasswordModel): MutableLiveData<String> {
        return callApi(api.resetPasswordReq(resetPasswordModel), Constants.RESET_PWD_REQ)
    }

    fun verifyPasswordReq(verifyPassModel: VerifyPassModel, token: String): MutableLiveData<String> {
        return callApi(api.verifyPassword(verifyPassModel, token), Constants.VERIFY_PWD_REQ)
    }

    fun getUserDetails(token: String): MutableLiveData<String> {
        return callApi(api.getUserDetails(token), Constants.USER_DETAIL)
    }

    fun markAttendance(markAttendanceModel: MarkAttendanceModel, token: String): MutableLiveData<String> {
        return callApi(api.markAttendance(markAttendanceModel, token), Constants.MARK_ATTENDANCE)
    }

}