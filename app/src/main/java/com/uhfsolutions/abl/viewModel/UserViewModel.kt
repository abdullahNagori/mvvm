package com.uhfsolutions.abl.viewModel

import androidx.lifecycle.ViewModel
import com.uhfsolutions.abl.network.ApiListener
import com.uhfsolutions.abl.repository.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    var apiListener: ApiListener? = null


}