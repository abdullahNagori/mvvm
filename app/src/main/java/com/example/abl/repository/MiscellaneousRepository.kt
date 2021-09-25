package com.example.abl.repository

import androidx.lifecycle.MutableLiveData
import com.example.abl.constant.Constants
import com.example.abl.model.location.UserLocation
import com.example.abl.network.Api
import com.example.abl.utils.SharedPrefManager
import javax.inject.Inject

class MiscellaneousRepository @Inject constructor(
    private val api: Api,
    private val sharedPrefManager: SharedPrefManager
) : BaseRepository() {

    fun getMarketingCollateral(): MutableLiveData<String> {
        return callApi(
            api.getmarketingcollateral("Bearer " + sharedPrefManager.getToken()),
            Constants.MARKETING_COLLATERAL
        )
    }

    fun uploadUserLocation(userLocation: List<UserLocation>): MutableLiveData<String> {
        return callApi(
            api.userLocation(userLocation, "Bearer " + sharedPrefManager.getToken()),
            Constants.UPDATE_LOCATION
        )

    }

    fun getDashboard(): MutableLiveData<String> {
        return callApi(
            api.getDashboard("Bearer " + sharedPrefManager.getToken()),
            Constants.DASHBOARD_COUNT
        )
    }

}