package com.uhfsolutions.abl.viewModel

import androidx.lifecycle.ViewModel
import com.uhfsolutions.abl.repository.LeadsRepository
import javax.inject.Inject

class LeadsViewModel @Inject constructor(private val leadsRepository: LeadsRepository) : ViewModel() {

}