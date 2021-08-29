package com.example.abl

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.abl.components.AppComponent
import com.example.abl.components.DaggerAppComponent
import com.example.abl.modules.AppInjector
import com.example.abl.modules.MyWorkerFactory
import com.example.abl.utils.Schedulers.LocationWorker
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App: Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    private lateinit var appComponent: AppComponent



    @Inject lateinit var workerConfiguration: Configuration

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


}