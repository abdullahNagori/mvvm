package com.example.abl.viewModel.coroutine

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.abl.constant.Constants
import com.example.abl.model.LovResponse
import com.example.abl.network.coroutine.WebResponse
import com.example.abl.repository.UserRepository
import com.example.abl.utils.GsonFactory
import com.example.abl.utils.SharedPrefManager
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class CoroutineViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    var handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + handler)

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    fun fetchLOV(): MutableLiveData<WebResponse> {
        val data = MutableLiveData<WebResponse>()
        data.postValue(WebResponse.Loading)

        scope.launch {
            try {
                val response = userRepository.getLovs()
                withContext(Dispatchers.Main) {
                    data.postValue(WebResponse.Success(response))
                    var gson = Gson()
//                    var mMineUserEntity = gson?.fromJson(data, LovResponse::class.java)
                   // val json = Gson().fromJson(data, LovResponse::class.java) as LovResponse
                    var abc = fromJson(response.toString())
                    Log.i("xxLovRes11", abc.toString())
                //    sharedPrefManager.setLeadStatus(response.body!!.company_lead_status)
                    withContext(Dispatchers.IO) {
                        Log.i("xxLovRes2", response.toString())
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

    private fun fromJson(json:String):LovResponse{
        return Gson().fromJson<LovResponse>(json, LovResponse::class.java)
    }

}