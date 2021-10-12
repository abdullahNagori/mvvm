package com.uhfsolutions.abl.modules

import com.uhfsolutions.abl.room.ABLDatabase
import com.uhfsolutions.abl.room.DAOAccess
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *  @author Abdullah Nagori
 */

@Module
object AppDaoModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideMyDao(myDB: ABLDatabase): DAOAccess {
        return myDB.leadDao()
    }
}