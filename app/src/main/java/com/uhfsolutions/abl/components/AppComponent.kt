package com.uhfsolutions.abl.components

import android.app.Application
import com.uhfsolutions.abl.App
import com.uhfsolutions.abl.modules.ActivityBuilderModule
import com.uhfsolutions.abl.modules.AppDaoModule
import com.uhfsolutions.abl.modules.AppDbModule
import com.uhfsolutions.abl.modules.ApplicationContextModule
import com.uhfsolutions.abl.network.NetworkModule
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
    ApplicationContextModule::class,
    ServiceBuilderModule::class])

interface AppComponent {

    fun inject(application: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}