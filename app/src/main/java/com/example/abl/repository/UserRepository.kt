package com.example.abl.repository

import androidx.lifecycle.MutableLiveData
import com.example.abl.constant.Constants
import com.example.abl.model.LoginModel
import com.example.abl.network.Api
import com.example.abl.utils.SharedPrefManager
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: Api, private val sharedPrefManager: SharedPrefManager):BaseRepository()
{
    fun login(loginModel: LoginModel): MutableLiveData<String> {
        return callApi(api.login(loginModel), Constants.LOGIN)
    }

}