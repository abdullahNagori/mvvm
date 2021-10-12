package com.uhfsolutions.abl.viewModel

import androidx.lifecycle.ViewModel
import com.uhfsolutions.abl.model.trainingAndQuiz.GetQuizModel
import com.uhfsolutions.abl.model.trainingAndQuiz.SubmitQuizModel
import com.uhfsolutions.abl.network.ApiListener
import com.uhfsolutions.abl.repository.TrainingRepository
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