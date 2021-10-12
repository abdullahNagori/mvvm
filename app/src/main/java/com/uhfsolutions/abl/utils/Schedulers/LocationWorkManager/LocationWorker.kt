package com.uhfsolutions.abl.utils.Schedulers.LocationWorkManager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.uhfsolutions.abl.model.generic.GenericMsgResponse
import com.uhfsolutions.abl.network.ApiListener
import com.uhfsolutions.abl.repository.MiscellaneousRepository
import com.uhfsolutions.abl.room.DAOAccess
import com.uhfsolutions.abl.utils.GsonFactory
import javax.inject.Inject

class LocationWorker @Inject constructor(
    private val miscellaneousRepository: MiscellaneousRepository,
    private val daoAccess: DAOAccess,
    var appContext: Context,
    var workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    var apiListener: ApiListener? = null

    override fun doWork(): Result {
        Log.i(TAG, "Fetching Data from Remote host")
        return try {
            val userList = daoAccess.getUserLocation()
            //userRepository.apiListener = apiListener
            val call = miscellaneousRepository.uploadUserLocation(userList)
            val response = GsonFactory.getConfiguredGson()?.fromJson(call.value, GenericMsgResponse::class.java)

            if (response?.message == "Successful") {
                   daoAccess.deleteUserLocation()
                Result.success()
            } else {
                Result.retry()
            }

        } catch (e: Throwable) {
            e.printStackTrace()
            // Technically WorkManager will return Result.failure()
            // but it's best to be explicit about it.
            // Thus if there were errors, we're return FAILURE
            Log.e(TAG, "Error fetching data", e)
            Result.failure()
        }
    }

    companion object {
        private val TAG = "LocationWorker"
    }

}