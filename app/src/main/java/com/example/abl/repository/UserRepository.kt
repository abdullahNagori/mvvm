package com.example.abl.repository

import androidx.lifecycle.MutableLiveData
import com.example.abl.constant.Constants
import com.example.abl.model.*
import com.example.abl.network.Api
import com.example.abl.network.coroutine.CoroutineLOVResponse
import com.example.abl.utils.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import java.lang.reflect.Type
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

    suspend fun getLovs(): LovResponse {
        return api.getLovs("Bearer " + sharedPrefManager.getToken())
    }

    fun getDynamicLeads(token: String): MutableLiveData<String> {
        return callApi(
            api.getLeadsForDynamicData("Bearer " + sharedPrefManager.getToken()),
            Constants.GET_DYNAMIC_LEADS
        )
    }

    suspend fun getLeads(): ArrayList<DynamicLeadsItem> {
        return api.getLeads("Bearer " + sharedPrefManager.getToken())
    }

     fun getTrainings(): MutableLiveData<String> {
        return callApi(api.getTrainings("Bearer " + sharedPrefManager.getToken()),Constants.TRAINING)
    }

    fun getQuizes(getQuizModel: GetQuizModel): MutableLiveData<String> {
        return callApi(api.getQuizes(getQuizModel,"Bearer " + sharedPrefManager.getToken()),Constants.MATERIAL)
    }

    fun submitQuiz(submitQuizModel: SubmitQuizModel): MutableLiveData<String> {
        return callApi(api.submitQuiz(submitQuizModel,"Bearer " + sharedPrefManager.getToken()),Constants.SUBMIT_QUIZ)
    }

     fun getVisitCalls(visitsCallModel: VisitsCallModel): Call<ArrayList<CheckinModel>> {
        return api.getVisitsCalls(visitsCallModel,"Bearer " + sharedPrefManager.getToken())
    }

    fun uploadUserLocation(userLocation: List<UserLocation>): MutableLiveData<String> {
        return callApi(
            api.userLocation(userLocation, "Bearer " + sharedPrefManager.getToken()),
            Constants.UPDATE_LOCATION
        )

    }

   fun addLead(customerDetail: CustomerDetail): Call<DynamicLeadsItem> {
        return api.addLead(customerDetail, "Bearer " + sharedPrefManager.getToken())
    }

    fun addLeadCheckin(checkinModel: CheckinModel): Call<GenericMsgResponse> {
        return api.addLeadCheckin(checkinModel, "Bearer " + sharedPrefManager.getToken())
    }

    fun getDashboard(): MutableLiveData<String> {
        return callApi(
            api.getDashboard("Bearer " + sharedPrefManager.getToken()),
            Constants.DASHBOARD_COUNT
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

    fun getMarketingCollateral(): MutableLiveData<String> {
        return callApi(
            api.getmarketingcollateral("Bearer " + sharedPrefManager.getToken()),
            Constants.MARKETING_COLLATERAL
        )
    }

}