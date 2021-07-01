package com.example.abl.modules

import com.example.abl.base.BaseDockFragment
import com.example.abl.fragment.LoginFragment
import com.example.abl.fragment.NewPasswordFragment
import com.example.abl.fragment.ReqPswrdFragment
import com.example.abl.fragment.OTPVerificationFragment
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

    @ContributesAndroidInjector
    fun contributeOTPFragment(): OTPVerificationFragment

    @ContributesAndroidInjector
    fun contributeResetPwdReqFragment(): ReqPswrdFragment

    @ContributesAndroidInjector
    fun contributeVerifyPwdReqFragment(): NewPasswordFragment
}