package com.example.abl.network

open class Response {
    object Loading : Response()
    data class Success<T>(val data: T) : Response()
    data class Error(val exception: String) : Response()
}