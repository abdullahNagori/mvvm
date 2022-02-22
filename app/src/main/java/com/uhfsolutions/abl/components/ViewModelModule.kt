package com.uhfsolutions.abl.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uhfsolutions.abl.keys.ViewModelKey
import com.uhfsolutions.abl.viewModel.*
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

}