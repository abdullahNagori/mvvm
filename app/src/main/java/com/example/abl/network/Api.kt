package com.example.abl.network

import com.example.abl.model.*
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
}