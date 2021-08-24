package com.example.abl.modules

import android.content.Context
import androidx.room.Room
import com.example.abl.room.ABLDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *  @author Abdullah Nagori
 */

@Module
object AppDbModule {
    @JvmStatic
    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): ABLDatabase {
        return Room.databaseBuilder(
            context,
            ABLDatabase::class.java, ABLDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}