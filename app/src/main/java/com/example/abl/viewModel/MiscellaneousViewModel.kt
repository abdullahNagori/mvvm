package com.example.abl.viewModel

import androidx.lifecycle.ViewModel
import com.example.abl.network.ApiListener
import com.example.abl.repository.MiscellaneousRepository
import com.example.abl.repository.UserRepository
import javax.inject.Inject

class MiscellaneousViewModel @Inject constructor(private val miscellaneousRepository: MiscellaneousRepository) : ViewModel() {

    var apiListener: ApiListener? = null

    fun getMarketingCollateral(){
        miscellaneousRepository.apiListener = apiListener
        miscellaneousRepository.getMarketingCollateral()
    }

    fun getDashBoard() {
        miscellaneousRepository.apiListener = apiListener
        miscellaneousRepository.getDashboard()
    }
}