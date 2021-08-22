package com.example.abl.network.coroutine

import com.google.gson.Gson

open class WebResponse {

    object Loading : WebResponse()
    data class Success<T>(val data: T) : WebResponse()
    data class Error(val exception: String) : WebResponse()

}