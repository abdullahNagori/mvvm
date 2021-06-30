package com.example.abl.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.abl.model.ResponseEnt
import com.example.abl.network.ApiListener
import com.example.abl.utils.GsonFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Abdullah Nagori
 */


open class BaseRepository {
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
                try {
                    if (response.isSuccessful && (response.code() == 200 || response.code() == 201)) {
                        apiResponse.value = response.body()?.string()

                        val responseEnt = GsonFactory.getConfiguredGson()?.fromJson(apiResponse.value, ResponseEnt::class.java)
                        if (responseEnt != null) {
                            if (responseEnt.message == "OK")
                                apiListener?.onSuccess(apiResponse, tag)
                            else
                                apiListener?.onFailure(responseEnt.message, tag)
                        } else
                            apiListener?.onFailure("Response body null", tag)
                    } else if (response.code() == 500)
                        apiListener?.onFailure("Internal Server Error", tag)
                    else
                        apiListener?.onFailure("Unknown Error", tag)
                } catch (ex: Exception) {
                    apiListener?.onFailure("Internal Server Error", tag)
                }
            }

        })
        return apiResponse
    }
}