package com.example.abl.viewModel

import androidx.lifecycle.ViewModel
import com.example.abl.model.trainingAndQuiz.GetQuizModel
import com.example.abl.model.trainingAndQuiz.SubmitQuizModel
import com.example.abl.network.ApiListener
import com.example.abl.repository.TrainingRepository
import com.example.abl.repository.UserRepository
import javax.inject.Inject

class TrainingViewModel @Inject constructor(private val trainingRepository: TrainingRepository) : ViewModel() {

    var apiListener: ApiListener? = null

    fun getTrainings() {
        trainingRepository.apiListener = apiListener
        trainingRepository.getTrainings()
    }

    fun getQuiz(getQuizModel: GetQuizModel) {
        trainingRepository.apiListener = apiListener
        trainingRepository.getQuizes(getQuizModel)
    }

    fun submitQuiz(submitQuizModel: SubmitQuizModel) {
        trainingRepository.apiListener = apiListener
        trainingRepository.submitQuiz(submitQuizModel)
    }
}