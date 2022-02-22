package com.uhfsolutions.abl.modules

import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 *  @author Abdullah Nagori
 */

@Module
interface
FragmentBuilderModule {

    @ContributesAndroidInjector
    fun contributeLoginFragment(): HomeFragment

    @ContributesAndroidInjector
    fun contributeBaseFragment(): BaseDockFragment

}