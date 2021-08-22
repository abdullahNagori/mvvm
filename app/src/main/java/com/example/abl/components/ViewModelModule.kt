package com.example.abl.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.abl.keys.ViewModelKey
import com.example.abl.viewModel.BaseViewModel
import com.example.abl.viewModel.CoroutineViewModel
import com.example.abl.viewModel.UserViewModel
import com.example.abl.viewModel.ViewModelFactory
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




}