package com.uhfsolutions.abl.repository

import androidx.lifecycle.MutableLiveData
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.model.changePassword.ChangePasswordModel
import com.uhfsolutions.abl.model.changePassword.VerifyPassModel
import com.uhfsolutions.abl.model.login.LoginModel
import com.uhfsolutions.abl.model.markAttendance.MarkAttendanceModel
import com.uhfsolutions.abl.model.otp.OtpModel
import com.uhfsolutions.abl.model.resetPassword.ResetPasswordModel
import com.uhfsolutions.abl.network.Api
import com.uhfsolutions.abl.utils.SharedPrefManager
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