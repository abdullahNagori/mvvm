package com.example.abl.network

import androidx.lifecycle.LiveData

/**
 * @author Abdullah Nagori
 */

interface ApiListener {
    fun onStarted();
    fun onSuccess(liveData: LiveData<String>, tag:String)
    fun onFailure(message:String,tag:String)
}