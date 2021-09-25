package com.example.abl.utils.Schedulers.LocationWorkManager

import androidx.work.DelegatingWorkerFactory
import com.example.abl.repository.LeadsRepository
import com.example.abl.repository.MiscellaneousRepository
import com.example.abl.repository.UserRepository
import com.example.abl.room.DAOAccess
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