package com.example.abl

import android.app.Application
import androidx.work.Configuration
import com.example.abl.components.AppComponent
import com.example.abl.components.DaggerAppComponent
import com.example.abl.modules.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App: Application(), HasAndroidInjector, Configuration.Provider {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    private lateinit var appComponent: AppComponent

    @Inject
    lateinit var workerConfiguration: Configuration

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)
        AppInjector.init(this)

//        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
//            "MyUniqueWorkName",
//            ExistingPeriodicWorkPolicy.KEEP,
//            LocationWorker)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

//    override fun getWorkManagerConfiguration() =
//        Configuration.Builder()
//            .setMinimumLoggingLevel(android.util.Log.DEBUG)
//            .setWorkerFactory(myWorkerFactory)
//            .build()

    override fun getWorkManagerConfiguration(): Configuration {
        return workerConfiguration
    }

}