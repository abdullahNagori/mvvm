package com.example.abl.modules

import com.example.abl.room.ABLDatabase
import com.example.abl.room.DAOAccess
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