package com.example.abl.modules

import androidx.work.DelegatingWorkerFactory
import com.example.abl.network.Api
import com.example.abl.network.NetworkModule
import com.example.abl.repository.UserRepository
import com.example.abl.room.DAOAccess
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyWorkerFactory @Inject constructor(
    userRepository: UserRepository,
    daoAccess: DAOAccess
) : DelegatingWorkerFactory() {
    init {
        addFactory(MyWorkerFactory(userRepository,daoAccess))
        // Add here other factories that you may need in your application
    }
}