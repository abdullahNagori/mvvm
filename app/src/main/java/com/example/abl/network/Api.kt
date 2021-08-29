package com.example.abl.network

import com.example.abl.model.*
import com.example.abl.network.coroutine.CoroutineLOVResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    //Login
    @POST("login")
    fun login(
        @Body loginModel: LoginModel): Call<ResponseBody>

    //OTP
    @POST("verifyOtp")
    fun verifyOtp(
        @Body otpModel: OtpModel
    ): Call<ResponseBody>

    //Reset Password Request
    @POST("resetPasswordRequest")
    fun resetPasswordReq(
        @Body resetPasswordModel: ResetPasswordModel
    ): Call<ResponseBody>

    //Verify Password Request
    @POST("auth/resetPasswordVerify")
    fun verifyPassword(
        @Body verifyPassModel: VerifyPassModel,@Header("Authorization") token: String
    ): Call<ResponseBody>

    //Get UserDetails
    @GET("auth/getUserDetails")
    fun getUserDetails( @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Verify Password Request
    @POST("auth/markAttendance")
    fun markAttendance(
        @Body markAttendanceModel: MarkAttendanceModel,@Header("Authorization") token: String
    ): Call<ResponseBody>

    //Get Lovs
    @GET("leads/getLovs")
   suspend fun getLovs(@Header("Authorization") token: String
    ): LovResponse

    //Get Dynamic Data
    @GET("leads/getLeadsForDynamicData")
    fun getLeadsForDynamicData(@Header("Authorization") token: String
    ): Call<ResponseBody>

    //Get Leads
    @GET("leads/getLeads")
   suspend fun getLeads(@Header("Authorization") token: String
    ): ArrayList<DynamicLeadsItem>

    //Add Leads
    @POST("leads/addLead")
    fun addLead(@Body customerDetail: CustomerDetail,
        @Header("Authorization") token: String
    ): Call<ResponseBody>


    //Add Lead Checkin
    @POST("leads/addLeadCheckin")
    fun addLeadCheckin(@Body checkinModel: CheckinModel,
                @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Add Lead Checkin
    @GET("leads/getDashboard")
    fun getDashboard( @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Change Password
    @POST("auth/changePassword")
    fun changePassword(@Body changePasswordModel: ChangePasswordModel,
                       @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Get Marketing Collateral
    @GET("marketingcollateral")
    fun getmarketingcollateral(@Header("Authorization") token: String
    ): Call<ResponseBody>

    //Get Marketing Collateral
    @GET("auth/userTracking")
   suspend fun userLocation(@Body customerDetail: List<UserLocation>, @Header("Authorization") token: String): Call<ResponseBody>
}