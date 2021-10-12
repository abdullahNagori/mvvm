package com.uhfsolutions.abl.viewModel.coroutine

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uhfsolutions.abl.model.addLead.DynamicLeadsItem
import com.uhfsolutions.abl.model.checkin.CheckinModel
import com.uhfsolutions.abl.model.lov.LovResponse
import com.uhfsolutions.abl.model.sync.SyncModel
import com.uhfsolutions.abl.model.visitLogs.VisitsCallModel
import com.uhfsolutions.abl.repository.LeadsRepository
import com.uhfsolutions.abl.room.RoomHelper
import com.uhfsolutions.abl.utils.SharedPrefManager
import kotlinx.coroutines.*
import retrofit2.Response
import java.lang.Exception
import java.time.LocalDate
import javax.inject.Inject

class CoroutineViewModel @Inject constructor(private val leadsRepository: LeadsRepository) :
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

                    val callLov = async { leadsRepository.getLovs() }
                    val callLeads = async { leadsRepository.getLeads() }
                    val visitCall = async {
                        leadsRepository.getVisitCalls(
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