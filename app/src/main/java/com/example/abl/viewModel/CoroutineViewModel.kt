package com.example.abl.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abl.constant.Constants
import com.example.abl.model.lov.LovResponse
import com.example.abl.network.coroutines.WebResponse
import com.example.abl.repository.UserRepository
import com.example.abl.room.RoomHelper
import com.example.abl.utils.GsonFactory
import com.example.abl.utils.SharedPrefManager
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoroutineViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    var handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + handler)

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    @Inject
    lateinit var roomHelper: RoomHelper

    fun getLOV(): MutableLiveData<WebResponse> {
        val data = MutableLiveData<WebResponse>()
        //  data.postValue(WebResponse.Loading)
        viewModelScope.launch {
            supervisorScope {
                try {
                    val callLov = async {  userRepository.getLovs()}
//                    val callLeads = async {  userRepository.getLeads()}
//
//                    val leadResponse: List<DynamicLeadsItem>? = try {
//                        // data.postValue(WebResponse.Success(responseLeads.await()))
//                        callLeads.await()
//                    } catch (ex: Exception) {
//                        null
//                    }
//
                    val lovResponse: LovResponse? = try {
                        //  data.postValue(WebResponse.Success(responseLov.await()))
                        callLov.await()
                    } catch (ex: Exception) {
                        null
                    }

                    processData(lovResponse!!)
//
//                    val response = userRepository.getLovs()
//
//                    withContext(Dispatchers.Main) {
//                        data.postValue(WebResponse.Success(response))
//                        Log.i("xxLeadRes1", response.toString())
//                        withContext(Dispatchers.IO) {
//                            Log.i("xxLeadRes2", response.toString())
//                        }
                    //   }
                } catch (e: Exception) {
//                    withContext(Dispatchers.Main) {
//                        data.postValue(WebResponse.Error(getException(e)))
//                    }
                    Log.i("Error", e.message.toString())
                }
            }
        }
        return data
    }

    private fun processData(lovResponse: LovResponse) {

        var lov = GsonFactory.getConfiguredGson()?.toJson(lovResponse)
        val array: List<String> = lov?.toCharArray()?.map { it.toString() }?.toTypedArray()?.toList()!!

//        roomHelper.insertLeadStatus(lovResponse.company_lead_status)

//        sharedPrefManager.setLeadStatus(lovResponse.company_lead_status)
//        if (dynamicLeadsItem != null) {
//            sharedPrefManager.setLeadData(dynamicLeadsItem)
//        }

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
            } catch (e: Exception){
                withContext(Dispatchers.Main) {
                    data.postValue(WebResponse.Error(getException(e)))
                }
            }
        }

        return data
    }

    fun getException(exception: Exception): String {
        var message = ""
        when (exception){
            is IOException -> message = Constants.NETWORK_ERROR
            is HttpException -> message = getErrorMessage((exception as HttpException).response()!!.errorBody()!!)
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