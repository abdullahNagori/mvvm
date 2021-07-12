package com.example.abl.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import javax.inject.Inject

class SharedPrefManager @Inject constructor(private val context: Context) {
    private val Key_Pref = "Key_Pref"
    private val KEY_USER_ID = "KEY_USER_ID"
    private val KEY_SECTION = "KEY_SECTION"
    private val AGENT_ID = "KEY_AGENT_ID"
    private val TOKEN = "KEY_TOKEN"
    private val KEY_USER = "KEY_USER"
    private val KEY_USERNAME = "KEY_USERNAME"
    private val KEY_SHIFT_START = "KEY_SHIFT_START"
    private val KEY_LOGIN_ID = "KEY_LOGIN_ID"
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(Key_Pref, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()




    fun storeUserId(user_id: String): Boolean {
        editor.putString(KEY_USER_ID, user_id);
        editor.apply();
        return true;
    }

    fun setSection(num: Int): Boolean{
        editor.putInt(KEY_SECTION, num);
        editor.apply();
        return true;
    }

    fun getSection():Int{
        return sharedPreferences.getInt(KEY_SECTION,0)!!
    }

    fun setToken(token: String): Boolean{
        editor.putString(TOKEN, token);
        editor.apply();
        return true;
    }

    fun getToken():String{
        return sharedPreferences.getString(TOKEN,"")!!
    }

    fun setShiftStart(isShiftStart: Boolean): Boolean{
        editor.putBoolean(KEY_SHIFT_START, isShiftStart);
        editor.apply();
        return true;
    }

    fun setLoginID(userID: String?): Boolean{
        editor.putString(KEY_LOGIN_ID, userID);
        editor.apply();
        return true
    }

    fun getLoginID():String?{
        return sharedPreferences.getString(KEY_LOGIN_ID,"")!!
    }

    fun getShiftStart():Boolean{
        return sharedPreferences.getBoolean(KEY_SHIFT_START,false)
    }

    fun setUsername(name: String?): Boolean{
        editor.putString(KEY_USERNAME, name);
        editor.apply();
        return true;
    }

    fun getUsername():String?{
        return sharedPreferences.getString(KEY_USERNAME,"")!!
    }

    fun getUserId():String{
        return sharedPreferences.getString(KEY_USER_ID,"")!!
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