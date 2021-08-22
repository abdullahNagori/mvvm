package com.example.abl.components

import android.content.Context
import com.example.abl.network.Api
import com.example.abl.room.DAOAccess
import com.example.abl.room.RoomHelper
import com.example.abl.utils.*
import com.example.abl.utils.Schedulers.BaseScheduler
import com.example.abl.utils.Schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 *  @author Abdullah Nagori
 */

@Module(includes = [(ViewModelModule::class)])
class AppModule {
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduler(): BaseScheduler {
        return SchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideSharedPrefManager(context: Context): SharedPrefManager {
        return SharedPrefManager(context)
    }

    @Provides
    @Singleton
    fun provideRoomHelper(daoAccess: DAOAccess): RoomHelper {
        return RoomHelper(daoAccess)
    }

    @Provides
    @Singleton
    fun provideInternetHelper(context: Context): InternetHelper {
        return InternetHelper(context)
    }

    @Provides
    @Singleton
    fun provideValidationHelper(context: Context): ValidationHelper {
        return ValidationHelper(context)
    }

    @Provides
    @Singleton
    fun provideUtilHelper(context: Context): UtilHelper {
        return UtilHelper(context)
    }


    @Provides
    @Singleton
    fun provideDateTimeFormatter(context: Context): DateTimeFormatter {
        return DateTimeFormatter(context)
    }
}