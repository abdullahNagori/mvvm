package com.uhfsolutions.abl.repository

import androidx.lifecycle.MutableLiveData
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.model.location.UserLocation
import com.uhfsolutions.abl.network.Api
import com.uhfsolutions.abl.utils.SharedPrefManager
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