package com.example.abl.viewModel

import androidx.lifecycle.ViewModel
import com.example.abl.repository.LeadsRepository
import javax.inject.Inject

class LeadsViewModel @Inject constructor(private val leadsRepository: LeadsRepository) : ViewModel() {
}