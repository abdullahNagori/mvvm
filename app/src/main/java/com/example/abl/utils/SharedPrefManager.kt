package com.example.abl.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.abl.model.CompanyLeadStatu
import com.example.abl.model.DynamicLeadsItem
import com.example.abl.model.DynamicLeadsResponse
import com.example.abl.model.UserDetailsResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

class SharedPrefManager @Inject constructor(private val context: Context) {
    private val Key_Pref = "Key_Pref"
    private val TOKEN = "KEY_TOKEN"
    private val USERNAME = "KEY_USERNAME"
    private val KEY_SHIFT_START = "KEY_SHIFT_START"
    private val KEY_USER_DETAILS = "KEY_USER_DETAILS"
    private val KEY_LEAD_STATUS = "KEY_LEAD_STATUS"
    private val KEY_LEAD_DATA = "KEY_LEAD_DATA"

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(Key_Pref, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun setToken(token: String): Boolean{
        editor.putString(TOKEN, token);
        editor.apply();
        return true;
    }

    fun setUsername(username: String){
        editor.putString(USERNAME, username);
        editor.apply();
    }

    fun getUsername(): String{
        return sharedPreferences.getString(USERNAME,"")!!
    }

    fun getToken():String{
        return sharedPreferences.getString(TOKEN,"")!!
    }

    fun setShiftStart(isShiftStart: Boolean): Boolean{
        editor.putBoolean(KEY_SHIFT_START, isShiftStart);
        editor.apply();
        return true;
    }

    fun getShiftStart():Boolean{
        return sharedPreferences.getBoolean(KEY_SHIFT_START,false)
    }

    fun setUserDetails(user: UserDetailsResponse): Boolean {
        editor.putString(KEY_USER_DETAILS, GsonFactory.getConfiguredGson()?.toJson(user))
        editor.apply();
        return true;
    }

    fun getUserDetails():UserDetailsResponse? {
        val json = sharedPreferences.getString(KEY_USER_DETAILS, "")
        return GsonFactory.getConfiguredGson()?.fromJson(json, UserDetailsResponse::class.java)
    }

    fun setLeadStatus(leadStatus: List<CompanyLeadStatu>): Boolean {
        editor.putString(KEY_LEAD_STATUS, GsonFactory.getConfiguredGson()?.toJson(leadStatus))
        editor.apply();
        return true;
    }

    fun getLeadStatus():List<CompanyLeadStatu>? {
        val json = sharedPreferences.getString(KEY_LEAD_STATUS, "")
        val listType: Type = object : TypeToken<List<CompanyLeadStatu>>() {}.type
        return GsonFactory.getConfiguredGson()?.fromJson(json, listType)
    }

    fun setLeadData(data: List<DynamicLeadsItem>): Boolean {
        editor.putString(KEY_LEAD_DATA, GsonFactory.getConfiguredGson()?.toJson(data))
        editor.apply();
        return true;
    }

    fun getLeadData():List<DynamicLeadsItem>? {
        val json = sharedPreferences.getString(KEY_LEAD_DATA, "")
        val listType: Type = object : TypeToken<List<DynamicLeadsItem>>() {}.type
        return GsonFactory.getConfiguredGson()?.fromJson(json, listType)
    }

    fun logout(): Boolean {
        editor.clear()
        editor.apply()
        return true
    }

    fun clear(): Boolean {
        editor.clear()
        editor.apply()
        return true
    }
}