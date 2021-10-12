package com.uhfsolutions.abl.viewModel

import androidx.lifecycle.ViewModel
import com.uhfsolutions.abl.network.ApiListener
import com.uhfsolutions.abl.repository.MiscellaneousRepository
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