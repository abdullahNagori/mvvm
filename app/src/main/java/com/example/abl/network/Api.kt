package com.example.abl.network

import com.example.abl.model.LoginModel
import com.example.abl.model.OtpModel
import com.example.abl.model.ResetPasswordModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}