package com.uhfsolutions.abl.modules

import androidx.work.DelegatingWorkerFactory
import com.uhfsolutions.abl.repository.UserRepository
import com.uhfsolutions.abl.room.DAOAccess
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