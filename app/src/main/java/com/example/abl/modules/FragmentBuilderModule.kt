package com.example.abl.modules

import com.example.abl.base.BaseDockFragment
import com.example.abl.fragment.*
import com.example.abl.fragment.customerDetail.*
import com.example.abl.fragment.dashboard.DashboardFragment
import com.example.abl.fragment.leadManagement.*
import com.example.abl.fragment.login.*
import com.example.abl.fragment.marketingCollateral.MarketingCollateralFragment
import com.example.abl.fragment.marketingCollateral.MarketingCollateralItemFragment
import com.example.abl.fragment.notification.NotificationFragment
import com.example.abl.fragment.portfolio.LTDFragment
import com.example.abl.fragment.portfolio.MTDFragment
import com.example.abl.fragment.portfolio.PortfolioFragment
import com.example.abl.fragment.salesManagement.CallLogsFragment
import com.example.abl.fragment.salesManagement.VisitLogsFragment
import com.example.abl.fragment.trainingAndQuiz.*
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 *  @author Abdullah Nagori
 */

@Module
interface
FragmentBuilderModule {


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

    @ContributesAndroidInjector
    fun contributeForgotPasswordFragment(): ForgotPasswordFragment

    @ContributesAndroidInjector
    fun contributeWelcomeFragment(): WelcomeFragment

    @ContributesAndroidInjector
    fun contributeCRMFragment(): CRMFragment

    @ContributesAndroidInjector
    fun contributeAddLeadFragment(): AddLeadFragment

    @ContributesAndroidInjector
    fun contributeAllFragment(): AllFragment

    @ContributesAndroidInjector
    fun contributeDashboardFragment(): DashboardFragment

    @ContributesAndroidInjector
    fun contributeCheckinFragment(): CheckInFormFragment

    @ContributesAndroidInjector
    fun contributeCustomerInfoFragment(): CustomerInfoFragment

    @ContributesAndroidInjector
    fun contributeChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector
    fun contributeCallLogsFragment(): CallLogsFragment

    @ContributesAndroidInjector
    fun contributePreviousVisitFragment(): PreviousVisitFragment

    @ContributesAndroidInjector
    fun contributeMarketingCollateralFragment(): MarketingCollateralFragment

    @ContributesAndroidInjector
    fun contributeMarketingCollateralItemFragment(): MarketingCollateralItemFragment

    @ContributesAndroidInjector
    fun contributeProductsFragment(): ProductsFragment

    @ContributesAndroidInjector
    fun contributeCalculatorFragment(): CalculatorFragment

    @ContributesAndroidInjector
    fun contributeMeetingQRFragment(): MeetingQRFragment

    @ContributesAndroidInjector
    fun contributeTaxCalculatorFragment(): TaxCalculatorFragment

    @ContributesAndroidInjector
    fun contributeInvestmentCalculatorFragment(): InvestmentCalculatorFragment

    @ContributesAndroidInjector
    fun contributeUpdateLocationFragment(): UpdateLocationFragment

    @ContributesAndroidInjector
    fun contributeUserTrackingFragment(): UserTrackingFragment

    @ContributesAndroidInjector
    fun contributeComprehensiveTrainingFragment(): MaterialTrainingFragment

    @ContributesAndroidInjector
    fun contributeTrainingFragment(): TrainingFragment

    @ContributesAndroidInjector
    fun contributeTrainingQuizFragment(): TrainingQuizFragment

    @ContributesAndroidInjector
    fun contributeTrainingQuizResultFragment(): TrainingQuizResultFragment

    @ContributesAndroidInjector
    fun contributeQuizResultFragment(): QuizResultFragment

    @ContributesAndroidInjector
    fun contributeQuizAnswerDetailFragment(): QuizAnswerDetailFragment

    @ContributesAndroidInjector
    fun contributeVisitLogsFragment(): VisitLogsFragment

    @ContributesAndroidInjector
    fun contributeQuizFragment(): QuizFragment

    @ContributesAndroidInjector
    fun contributeFollowupFragment(): FollowupFragment

    @ContributesAndroidInjector
    fun contributeLTDFragment(): LTDFragment

    @ContributesAndroidInjector
    fun contributeMTDFragment(): MTDFragment

    @ContributesAndroidInjector
    fun contributeNotificationFragment(): NotificationFragment

    @ContributesAndroidInjector
    fun contributePortfolioFragment(): PortfolioFragment
}