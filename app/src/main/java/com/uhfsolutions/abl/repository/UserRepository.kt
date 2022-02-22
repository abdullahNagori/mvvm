package com.uhfsolutions.abl.repository

import com.uhfsolutions.abl.network.Api
import com.uhfsolutions.abl.utils.SharedPrefManager
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: Api,
    private val sharedPrefManager: SharedPrefManager
) : BaseRepository() {


}