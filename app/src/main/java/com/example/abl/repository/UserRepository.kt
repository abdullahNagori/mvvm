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

class UserRepository @Inject constructor(private val api: Api, private val sharedPrefManager: SharedPrefManager): BaseRepository()
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

    fun verifyPasswordReq(verifyPassModel: VerifyPassModel): MutableLiveData<String> {
        return callApi(api.verifyPassword(verifyPassModel, "Bearer " + sharedPrefManager.getToken()), Constants.VERIFY_PWD_REQ)
    }

    fun getUserDetails(token: String): MutableLiveData<String> {
        return callApi(api.getUserDetails("Bearer " + sharedPrefManager.getToken()), Constants.USER_DETAIL)
    }

    fun markAttendance(markAttendanceModel: MarkAttendanceModel, token: String): MutableLiveData<String> {
        return callApi(api.markAttendance(markAttendanceModel, "Bearer " + sharedPrefManager.getToken()), Constants.MARK_ATTENDANCE)
    }

   suspend fun getLovs(): LovResponse {
       // return callApi(api.getLovs("Bearer " + sharedPrefManager.getToken()), Constants.GET_LOVS)
       return withContext(Dispatchers.IO) {
           async {
               api.getLovs("Bearer " + sharedPrefManager.getToken())
           }
       }.await()
    }

    fun getDynamicLeads(token: String): MutableLiveData<String> {
        return callApi(api.getLeadsForDynamicData("Bearer " + sharedPrefManager.getToken()), Constants.GET_DYNAMIC_LEADS)
    }

    suspend fun getLeads(): ArrayList<DynamicLeadsItem> {
        return withContext(Dispatchers.IO) {
            async {
                api.getLeads("Bearer " + sharedPrefManager.getToken())
            }
        }.await()
    }

     fun uploadUserLocation(userLocation: List<UserLocation>): MutableLiveData<String> {
//        return withContext(Dispatchers.IO) {
//            async {
                 return callApi(api.userLocation(userLocation,"Bearer " + sharedPrefManager.getToken()), Constants.UPDATE_LOCATION)
//            }
//        }.await()
    }

    fun addLead(customerDetail: CustomerDetail): MutableLiveData<String> {
        return callApi(api.addLead(customerDetail, "Bearer " + sharedPrefManager.getToken()), Constants.ADD_LEAD)
    }

    fun addLeadCheckin(checkinModel: CheckinModel): MutableLiveData<String> {
        return callApi(api.addLeadCheckin(checkinModel, "Bearer " + sharedPrefManager.getToken()), Constants.ADD_LEAD_CHECKIN)
    }

    fun getDashboard(): MutableLiveData<String> {
        return callApi(api.getDashboard( "Bearer " + sharedPrefManager.getToken()), Constants.DASHBOARD_COUNT)
    }

    fun changePassword(changePasswordModel: ChangePasswordModel): MutableLiveData<String> {
        return callApi(api.changePassword(changePasswordModel,"Bearer " + sharedPrefManager.getToken()), Constants.CHANGE_PASSWORD)
    }

    fun getMarketingCollateral(): MutableLiveData<String> {
        return callApi(api.getmarketingcollateral("Bearer " + sharedPrefManager.getToken()), Constants.MARKETING_COLLATERAL)
    }

}