package com.example.abl.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.abl.model.ErrorResponseEnt
import com.example.abl.model.ResponseEnt
import com.example.abl.network.ApiListener
import com.example.abl.utils.GsonFactory
import com.example.abl.utils.UtilHelper
import com.example.abl.utils.ValidationHelper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

/**
 * @author Abdullah Nagori
 */


open class BaseRepository {

    @Inject
    lateinit var validationhelper: ValidationHelper
    var apiResponse = MutableLiveData<String>()
    var apiListener: ApiListener? = null

    fun callApi(api: Call<ResponseBody>, tag: String): MutableLiveData<String> {
        apiListener?.onStarted()
        api.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                apiResponse.value = t.message
                Log.i("xx1",t.message.toString())
                apiListener?.onFailure(t.message!!, tag)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful && (response.code() == 200 || response.code() == 201)) {
                    apiResponse.value = response.body()?.string()
                       Log.d("ResponseBody",response.body()!!.string())
                    apiListener?.onSuccess(apiResponse, tag)
                } else if (response.code() == 500)
                    apiListener?.onFailure("Internal Server Error", tag)
                else if(response.code() == 400)
                    validationhelper.navigateToLogin()
                else if(response.code() == 551)
                    validationhelper.navigateToLogin()
                else if(response.code() == 552)
                    validationhelper.navigateToChangePassword()
                else {
                    try {
                        apiResponse.value = response.errorBody()?.string()
                        val errorResponseEnt = GsonFactory.getConfiguredGson()?.fromJson(apiResponse.value, ErrorResponseEnt::class.java)
                        if (errorResponseEnt?.error == "Provided token is expired." )
                            apiListener?.onFailure(errorResponseEnt.error, tag)
                        else
                            apiListener?.onFailure("Unknown Error", tag)
                    }
                    catch (e: Exception){
                        apiListener?.onFailure("Internal Server Error", tag)
                    }
                }
            }
        })
        return apiResponse
    }
}