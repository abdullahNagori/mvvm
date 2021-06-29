package com.example.abl.modules

import com.example.abl.activity.DockActivity
import com.example.abl.activity.LoginActivity
import com.example.abl.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *  @author Abdullah Nagori
 */

@Module
interface ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    fun contributeDockActivity(): DockActivity

    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    fun contributeLoginActivity(): LoginActivity

}