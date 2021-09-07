package com.example.abl.viewModel.coroutine

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.abl.model.addLead.DynamicLeadsItem
import com.example.abl.model.checkin.CheckinModel
import com.example.abl.model.lov.LovResponse
import com.example.abl.model.sync.SyncModel
import com.example.abl.model.visitLogs.VisitsCallModel
import com.example.abl.repository.UserRepository
import com.example.abl.room.RoomHelper
import com.example.abl.utils.SharedPrefManager
import kotlinx.coroutines.*
import retrofit2.Response
import java.lang.Exception
import java.time.LocalDate
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