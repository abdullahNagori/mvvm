package com.example.abl.viewModel.coroutine

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Worker
import com.example.abl.constant.Constants
import com.example.abl.model.*
import com.example.abl.network.coroutine.WebResponse
import com.example.abl.repository.UserRepository
import com.example.abl.room.RoomHelper
import com.example.abl.utils.GsonFactory
import com.example.abl.utils.SharedPrefManager
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CoroutineViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    var handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + handler)

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    @Inject
    lateinit var roomHelper: RoomHelper


    @SuppressLint("NewApi")
    fun getLOV(): MutableLiveData<SyncModel> {
        val data = MutableLiveData<SyncModel>()
        CoroutineScope(Dispatchers.IO).launch {
            supervisorScope {
                try {

                    val toDate = LocalDate.now()
                    val fromDate = toDate.minusMonths(1)

                    val callLov = async { userRepository.getLovs() }
                    val callLeads = async { userRepository.getLeads() }
                    val visitCall = async {
                        userRepository.getVisitCalls(
                            VisitsCallModel(
                                "all",
                                fromDate.toString(),
                                toDate.toString(),

                                )
                        ).execute()
                    }
//                    val dashboardCount = async { userRepository.getDashboard().execute() }

                    val leadResponse: ArrayList<DynamicLeadsItem>? = try {
                        callLeads.await()
                    } catch (ex: Exception) {
                        null
                    }

                    val lovResponse: LovResponse? = try {
                        callLov.await()
                    } catch (ex: Exception) {
                        null
                    }

                    val visitCallResponse: Response<ArrayList<CheckinModel>>? = try {
                        visitCall.await()
                    } catch (ex: Exception) {
                        null
                    }

//                    val dashboardCallResponse: Response<DashboardResponse>? = try {
//                        dashboardCount.await()
//                    } catch (ex: Exception) {
//                        null
//                    }

                    if (lovResponse != null) {
                            data.postValue(
                                SyncModel(
                                    leadResponse,
                                    lovResponse,
                                    visitCallResponse?.body()
                                )
                            )

                    }
                } catch (e: Exception) {
                    Log.i("Error", e.message.toString())
                }
            }
        }
        return data
    }
}