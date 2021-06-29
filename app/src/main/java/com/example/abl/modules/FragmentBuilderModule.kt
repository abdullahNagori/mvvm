package com.example.abl.modules

import com.example.abl.base.BaseDockFragment
import com.example.abl.fragment.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 *  @author Abdullah Nagori
 */

@Module
interface FragmentBuilderModule {


    @ContributesAndroidInjector
    fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    fun contributeBaseFragment(): BaseDockFragment
}