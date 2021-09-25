package com.example.abl.repository

import androidx.lifecycle.MutableLiveData
import com.example.abl.constant.Constants
import com.example.abl.model.lov.LovResponse
import com.example.abl.model.trainingAndQuiz.GetQuizModel
import com.example.abl.model.trainingAndQuiz.SubmitQuizModel
import com.example.abl.network.Api
import com.example.abl.utils.SharedPrefManager
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