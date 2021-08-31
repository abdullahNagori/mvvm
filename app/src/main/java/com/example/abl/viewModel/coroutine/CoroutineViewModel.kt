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
import retrofit2.HttpException
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
        viewModelScope.launch {
            supervisorScope {
                try {

                    val currentDate = LocalDate.now()
                    val formatters = DateTimeFormatter.ofPattern("dd-MM-uuuu")
                    val fromDate: String = currentDate.format(formatters)
                    val toDate: String = currentDate.minusMonths(1).toString().format(formatters)

                    val callLov = async { userRepository.getLovs() }
                    val callLeads = async { userRepository.getLeads() }
                    val callVisits = async {
                        userRepository.getVisitCalls(
                            VisitsCallModel(
                                "all",
                                fromDate,
                                toDate
                            )
                        )
                    }

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

                    val visitCallResponse: ArrayList<VisitsCallResponseItem>? = try {
                        callVisits.await()
                    } catch (ex: Exception) {
                        null
                    }

                    if (lovResponse != null && visitCallResponse != null) {
                        data.value = SyncModel(leadResponse, lovResponse, visitCallResponse)
                    }
                } catch (e: Exception) {
                    Log.i("Error", e.message.toString())
                }
            }
        }
        return data
    }


    fun getLeads(): MutableLiveData<WebResponse> {

        val data = MutableLiveData<WebResponse>()
        data.postValue(WebResponse.Loading)

        scope.launch {
            try {
                val response = userRepository.getLeads()
                withContext(Dispatchers.Main) {
                    data.postValue(WebResponse.Success(response))
                    Log.i("xxLeadRes1", response.toString())
                    withContext(Dispatchers.IO) {
                        Log.i("xxLeadRes2", response.toString())
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    data.postValue(WebResponse.Error(getException(e)))
                }
            }
        }

        return data
    }

    fun getException(exception: Exception): String {
        var message = ""
        when (exception) {
            is IOException -> message = Constants.NETWORK_ERROR
            is HttpException -> message =
                getErrorMessage((exception as HttpException).response()!!.errorBody()!!)
            else -> message = "Something went wrong"
        }
        return message
    }

    private fun getErrorMessage(json: ResponseBody): String {
        val obj = JSONObject(json.string())
        val error = obj.getJSONObject("header").getString("message")
        return error.toString()
    }
}