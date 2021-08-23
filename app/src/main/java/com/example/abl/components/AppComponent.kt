package com.example.abl.components

import android.app.Application
import com.example.abl.App
import com.example.abl.modules.ActivityBuilderModule
import com.example.abl.modules.AppDaoModule
import com.example.abl.modules.AppDbModule
import com.example.abl.modules.ApplicationContextModule
import com.example.abl.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 *  @author Abdullah Nagori
 */

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    AppDbModule::class,
    AppDaoModule::class,
    ActivityBuilderModule::class,
    ApplicationContextModule::class])

interface AppComponent {

    fun inject(application: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}