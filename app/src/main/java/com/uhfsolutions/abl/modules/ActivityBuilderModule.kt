package com.uhfsolutions.abl.modules

import com.uhfsolutions.abl.activity.*
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
    fun contributeMainActivity(): MainActivity


}