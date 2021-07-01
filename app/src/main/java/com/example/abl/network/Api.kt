package com.example.abl.network

import com.example.abl.model.LoginModel
import com.example.abl.model.OtpModel
import com.example.abl.model.ResetPasswordModel
import com.example.abl.model.VerifyPassModel
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
}