package com.example.abl.network

import com.example.abl.model.LoginModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    //Login
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Body loginModel: LoginModel): Call<ResponseBody>
}