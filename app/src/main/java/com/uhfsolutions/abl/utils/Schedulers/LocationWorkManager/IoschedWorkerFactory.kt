package com.uhfsolutions.abl.utils.Schedulers.LocationWorkManager

import androidx.work.DelegatingWorkerFactory
import com.uhfsolutions.abl.repository.LeadsRepository
import com.uhfsolutions.abl.repository.MiscellaneousRepository
import com.uhfsolutions.abl.room.DAOAccess
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IoschedWorkerFactory @Inject constructor(
    miscellaneousRepository: MiscellaneousRepository, daoAccess: DAOAccess,
    leadsRepository: LeadsRepository
) : DelegatingWorkerFactory() {
    init {
        addFactory(LocationWorkerFactory(daoAccess, miscellaneousRepository,leadsRepository))
    }
}