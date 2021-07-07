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
        return callApi(api.getUserDetails("Bearer " + sharedPrefManager.getToken()), Constants.USER_DETAIL)
    }

    fun markAttendance(markAttendanceModel: MarkAttendanceModel, token: String): MutableLiveData<String> {
        return callApi(api.markAttendance(markAttendanceModel, "Bearer " + sharedPrefManager.getToken()), Constants.MARK_ATTENDANCE)
    }

    fun getLovs(token: String): MutableLiveData<String> {
        return callApi(api.getLovs("Bearer " + sharedPrefManager.getToken()), Constants.GET_LOVS)
    }

    fun getDynamicLeads(token: String): MutableLiveData<String> {
        return callApi(api.getLeadsForDynamicData("Bearer " + sharedPrefManager.getToken()), Constants.GET_DYNAMIC_LEADS)
    }

    fun addLead(addLeadModelItem: AddLeadModelItem,token: String): MutableLiveData<String> {
        return callApi(api.addLead(addLeadModelItem, "Bearer " + sharedPrefManager.getToken()), Constants.ADD_LEAD)
    }

}