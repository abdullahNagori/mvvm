package com.example.abl.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefManager @Inject constructor(private val context: Context) {
    private val Key_Pref = "Key_Pref"
    private val KEY_USER_ID = "KEY_USER_ID"
    private val AGENT_ID = "KEY_AGENT_ID"
    private val TOKEN = "KEY_TOKEN"
    private val KEY_USER = "KEY_USER"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(Key_Pref, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()


    fun storeUserId(user_id: String): Boolean {
        editor.putString(KEY_USER_ID, user_id);
        editor.apply();
        return true;
    }

    fun storeAgentID(agent_id: String): Boolean{
        editor.putString(AGENT_ID, agent_id);
        editor.apply();
        return true;
    }

    fun setToken(token: String): Boolean{
        editor.putString(TOKEN, token);
        editor.apply();
        return true;
    }

    fun getToken():String{
        return sharedPreferences.getString(TOKEN,"")!!
    }

    fun getUserId():String{
        return sharedPreferences.getString(KEY_USER_ID,"")!!
    }

    fun getAgentID():String{
        return sharedPreferences.getString(AGENT_ID,"")!!
    }

    fun isLoggedIn(): Boolean {
        if (sharedPreferences.getString(KEY_USER_ID, null) != null) return true
        return false
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