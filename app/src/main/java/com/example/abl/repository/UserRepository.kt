package com.example.abl.repository

import androidx.lifecycle.MutableLiveData
import com.example.abl.constant.Constants
import com.example.abl.model.addLead.CustomerDetail
import com.example.abl.model.addLead.DynamicLeadsItem
import com.example.abl.model.changePassword.ChangePasswordModel
import com.example.abl.model.changePassword.VerifyPassModel
import com.example.abl.model.checkin.CheckinModel
import com.example.abl.model.generic.GenericMsgResponse
import com.example.abl.model.location.UserLocation
import com.example.abl.model.login.LoginModel
import com.example.abl.model.lov.LovResponse
import com.example.abl.model.markAttendance.MarkAttendanceModel
import com.example.abl.model.otp.OtpModel
import com.example.abl.model.resetPassword.ResetPasswordModel
import com.example.abl.model.trainingAndQuiz.GetQuizModel
import com.example.abl.model.trainingAndQuiz.SubmitQuizModel
import com.example.abl.model.visitLogs.VisitsCallModel
import com.example.abl.network.Api
import com.example.abl.utils.SharedPrefManager
import retrofit2.Call
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: Api,
    private val sharedPrefManager: SharedPrefManager
) : BaseRepository() {

    fun login(loginModel: LoginModel): MutableLiveData<String> {
        return callApi(api.login(loginModel), Constants.LOGIN)
    }

    fun verifyOpt(otpModel: OtpModel): MutableLiveData<String> {
        return callApi(api.verifyOtp(otpModel), Constants.VERIFY_OTP)
    }

    fun resetPasswordReq(resetPasswordModel: ResetPasswordModel): MutableLiveData<String> {
        return callApi(api.resetPasswordReq(resetPasswordModel), Constants.RESET_PWD_REQ)
    }

    fun verifyPasswordReq(verifyPassModel: VerifyPassModel): MutableLiveData<String> {
        return callApi(
            api.verifyPassword(
                verifyPassModel,
                "Bearer " + sharedPrefManager.getToken()
            ), Constants.VERIFY_PWD_REQ
        )
    }

    fun getUserDetails(token: String): MutableLiveData<String> {
        return callApi(
            api.getUserDetails("Bearer " + sharedPrefManager.getToken()),
            Constants.USER_DETAIL
        )
    }

    fun markAttendance(
        markAttendanceModel: MarkAttendanceModel,
        token: String
    ): MutableLiveData<String> {
        return callApi(
            api.markAttendance(
                markAttendanceModel,
                "Bearer " + sharedPrefManager.getToken()
            ), Constants.MARK_ATTENDANCE
        )
    }

    fun changePassword(changePasswordModel: ChangePasswordModel): MutableLiveData<String> {
        return callApi(
            api.changePassword(
                changePasswordModel,
                "Bearer " + sharedPrefManager.getToken()
            ), Constants.CHANGE_PASSWORD
        )
    }

}