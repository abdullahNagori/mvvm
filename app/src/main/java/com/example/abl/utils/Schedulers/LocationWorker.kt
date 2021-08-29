package com.example.abl.utils.Schedulers

import android.R
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.abl.model.UserLocation
import com.example.abl.network.coroutine.WebResponse
import com.example.abl.repository.UserRepository
import com.example.abl.room.DAOAccess
import com.example.abl.viewModel.UserViewModel
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.inject.Inject

class LocationWorker @Inject constructor(
    private val userRepository: UserRepository,
    private val daoAccess: DAOAccess,
    var appContext: Context,
    var workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        //simulate slow work
        // WorkerUtils.makeStatusNotification("Fetching Data", applicationContext);
        Log.i(TAG, "Fetching Data from Remote host")
         //WorkerUtils.sleep()
        return try {
            val userList = daoAccess.getUserLocation()

            val call = userRepository.uploadUserLocation(userList)

            if (call.isExecuted) {
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
//
//    init {
//        bookService = App.get().getBookService()
//        bookDao = App.get().getBookDao()
//    }
}