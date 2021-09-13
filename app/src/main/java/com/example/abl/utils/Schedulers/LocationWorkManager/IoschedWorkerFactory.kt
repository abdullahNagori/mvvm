package com.example.abl.utils.Schedulers.LocationWorkManager

import androidx.work.DelegatingWorkerFactory
import com.example.abl.repository.UserRepository
import com.example.abl.room.DAOAccess
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IoschedWorkerFactory @Inject constructor(
    userRepository: UserRepository, daoAccess: DAOAccess
) : DelegatingWorkerFactory() {
    init {
        addFactory(LocationWorkerFactory(daoAccess, userRepository))
    }
}