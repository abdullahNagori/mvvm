package com.uhfsolutions.abl.components

import com.uhfsolutions.abl.location.ForegroundOnlyLocationService
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *  @author Abdullah Nagori
 */

@Module
internal abstract class ServiceBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMyService(): ForegroundOnlyLocationService
}