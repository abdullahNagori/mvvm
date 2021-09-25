package com.example.abl.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.abl.keys.ViewModelKey
import com.example.abl.viewModel.*
import com.example.abl.viewModel.coroutine.CoroutineViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 *  @author Abdullah Nagori
 */

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BaseViewModel::class)
    fun bindBaseViewModel(baseViewModel: BaseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    fun bindUserViewModel(userViewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CoroutineViewModel::class)
    fun bindCoroutineViewModel(coroutineViewModel: CoroutineViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LeadsViewModel::class)
    fun bindLeadsViewModel(leadViewModel: LeadsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainingViewModel::class)
    fun bindTrainingViewModel(trainingViewModel: TrainingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MiscellaneousViewModel::class)
    fun bindMiscellaneousViewModel(miscellaneousViewModel: MiscellaneousViewModel): ViewModel
}