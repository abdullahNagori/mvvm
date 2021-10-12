package com.uhfsolutions.abl.network

import com.uhfsolutions.abl.model.addLead.CustomerDetail
import com.uhfsolutions.abl.model.addLead.DynamicLeadsItem
import com.uhfsolutions.abl.model.changePassword.ChangePasswordModel
import com.uhfsolutions.abl.model.changePassword.VerifyPassModel
import com.uhfsolutions.abl.model.checkin.CheckinModel
import com.uhfsolutions.abl.model.generic.GenericMsgResponse
import com.uhfsolutions.abl.model.location.UserLocation
import com.uhfsolutions.abl.model.login.LoginModel
import com.uhfsolutions.abl.model.lov.LovResponse
import com.uhfsolutions.abl.model.markAttendance.MarkAttendanceModel
import com.uhfsolutions.abl.model.otp.OtpModel
import com.uhfsolutions.abl.model.resetPassword.ResetPasswordModel
import com.uhfsolutions.abl.model.trainingAndQuiz.GetQuizModel
import com.uhfsolutions.abl.model.trainingAndQuiz.SubmitQuizModel
import com.uhfsolutions.abl.model.visitLogs.VisitsCallModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    //Login
    @POST("login")
    fun login(
        @Body loginModel: LoginModel
    ): Call<ResponseBody>

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
        @Body verifyPassModel: VerifyPassModel, @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Get UserDetails
    @GET("auth/getUserDetails")
    fun getUserDetails(
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Verify Password Request
    @POST("auth/markAttendance")
    fun markAttendance(
        @Body markAttendanceModel: MarkAttendanceModel, @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Get Lovs
    @GET("leads/getLovs")
    suspend fun getLovs(
        @Header("Authorization") token: String
    ): LovResponse

    //Get Dynamic Data
    @GET("leads/getLeadsForDynamicData")
    fun getLeadsForDynamicData(
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Get Leads
    @GET("leads/getLeads")
    suspend fun getLeads(
        @Header("Authorization") token: String
    ): ArrayList<DynamicLeadsItem>

    //Add Leads
    @POST("leads/addLead")
    fun addLead(
        @Body customerDetail: CustomerDetail,
        @Header("Authorization") token: String
    ): Call<DynamicLeadsItem>


    //Add Lead Checkin
    @POST("leads/addLeadCheckin")
    fun addLeadCheckin(
        @Body checkinModel: CheckinModel,
        @Header("Authorization") token: String
    ): Call<GenericMsgResponse>

    //Add Lead Checkin
    @GET("leads/getDashboard")
    fun getDashboard(
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Change Password
    @POST("auth/changePassword")
    fun changePassword(
        @Body changePasswordModel: ChangePasswordModel,
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Get Marketing Collateral
    @GET("marketingcollateral")
    fun getmarketingcollateral(
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Upload UserTracking
    @POST("auth/userTracking")
    fun userLocation(
        @Body customerDetail: List<UserLocation>,
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Get Visits Calls
    @POST("leads/getVisitsCalls")
    fun getVisitsCalls(
        @Body visitsCallModel: VisitsCallModel,
        @Header("Authorization") token: String
    ): Call<ArrayList<CheckinModel>>

    //Get Trainings
    @POST("training/getTrainings")
    fun getTrainings(
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Get Trainings
    @POST("training/getQuizes")
    fun getQuizes(
        @Body getQuizModel: GetQuizModel,
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    //Get Trainings
    @POST("training/submitQuiz")
    fun submitQuiz(
        @Body submitQuizModel: SubmitQuizModel,
        @Header("Authorization") token: String
    ): Call<ResponseBody>
}