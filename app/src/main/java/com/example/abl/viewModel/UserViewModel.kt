package com.example.abl.viewModel

import androidx.lifecycle.ViewModel
import com.example.abl.model.LoginModel
import com.example.abl.network.ApiListener
import com.example.abl.repository.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    var apiListener: ApiListener? = null

    fun login(loginModel: LoginModel){
        userRepository.apiListener = apiListener
        userRepository.login(loginModel)
    }
}