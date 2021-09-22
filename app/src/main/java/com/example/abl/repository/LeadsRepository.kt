package com.example.abl.repository

import com.example.abl.network.Api
import com.example.abl.utils.SharedPrefManager
import javax.inject.Inject

class LeadsRepository @Inject constructor(
    private val api: Api,
    private val sharedPrefManager: SharedPrefManager
) : BaseRepository() {
}