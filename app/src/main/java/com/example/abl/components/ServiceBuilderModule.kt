package com.example.abl.components

import com.example.abl.location.ForegroundOnlyLocationService
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