package com.example.abl.network

import androidx.lifecycle.LiveData
import com.example.abl.model.DynamicLeadsItem

/**
 * @author Abdullah Nagori
 */

interface ApiListener {
    fun onStarted();
    fun onSuccess(liveData: LiveData<String>, tag:String)
    fun onFailure(message:String,tag:String)
    fun onFailureWithResponseCode(code: Int,message:String,tag: String)
    fun callDialog(type: String, contact: String?, dynamicLeadsItem: DynamicLeadsItem?)
    fun showPasswordchangingInstructions(text: String?)
}