package com.uhfsolutions.abl.repository

import androidx.lifecycle.MutableLiveData
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.model.trainingAndQuiz.GetQuizModel
import com.uhfsolutions.abl.model.trainingAndQuiz.SubmitQuizModel
import com.uhfsolutions.abl.network.Api
import com.uhfsolutions.abl.utils.SharedPrefManager
import javax.inject.Inject

class TrainingRepository @Inject constructor(
    private val api: Api,
    private val sharedPrefManager: SharedPrefManager
) : BaseRepository() {

    fun getTrainings(): MutableLiveData<String> {
        return callApi(api.getTrainings("Bearer " + sharedPrefManager.getToken()), Constants.TRAINING)
    }

    fun getQuizes(getQuizModel: GetQuizModel): MutableLiveData<String> {
        return callApi(api.getQuizes(getQuizModel,"Bearer " + sharedPrefManager.getToken()),
            Constants.MATERIAL)
    }

    fun submitQuiz(submitQuizModel: SubmitQuizModel): MutableLiveData<String> {
        return callApi(api.submitQuiz(submitQuizModel,"Bearer " + sharedPrefManager.getToken()),
            Constants.SUBMIT_QUIZ)
    }

}