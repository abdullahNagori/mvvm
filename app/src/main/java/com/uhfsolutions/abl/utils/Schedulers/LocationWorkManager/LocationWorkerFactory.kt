package com.uhfsolutions.abl.utils.Schedulers.LocationWorkManager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.uhfsolutions.abl.repository.LeadsRepository
import com.uhfsolutions.abl.repository.MiscellaneousRepository
import com.uhfsolutions.abl.room.DAOAccess
import com.uhfsolutions.abl.utils.Schedulers.UploadCheckInWorkManager.UploadCheckInWorker
import com.uhfsolutions.abl.utils.Schedulers.UploadLeadWorkManager.UploadLeadWorker
import javax.inject.Inject

class LocationWorkerFactory @Inject constructor(
    private val daoAccess: DAOAccess,
    private val miscellaneousRepository: MiscellaneousRepository,
    private val leadsRepository: LeadsRepository,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            LocationWorker::class.java.name -> LocationWorker(miscellaneousRepository, daoAccess, appContext, workerParameters)
            UploadLeadWorker::class.java.name -> UploadLeadWorker(leadsRepository, daoAccess, appContext, workerParameters)
            UploadCheckInWorker::class.java.name -> UploadCheckInWorker(leadsRepository, daoAccess, appContext, workerParameters)
            else ->
                // Return null, so that the base class can delegate to the default WorkerFactory.
                null
        }
    }
}