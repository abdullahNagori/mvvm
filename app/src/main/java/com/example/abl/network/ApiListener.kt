package com.example.abl.network

import androidx.lifecycle.LiveData
import com.example.abl.model.addLead.DynamicLeadsItem

/**
 * @author Abdullah Nagori
 */

interface ApiListener {
    fun onStarted();
    fun onSuccess(liveData: LiveData<String>, tag:String)
    fun onFailure(message:String,tag:String)
    fun onFailureWithResponseCode(code: Int,message:String,tag: String)
    fun showPasswordChangingInstructions(text: String?)
}