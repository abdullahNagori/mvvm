package com.example.abl.fragment.trainingAndQuiz

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.adapter.TrainingListAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.TrainingFragmentBinding
import com.example.abl.model.trainingAndQuiz.TrainingResponse
import com.example.abl.utils.GsonFactory

class TrainingFragment : BaseDockFragment(), ClickListner {

    lateinit var binding: TrainingFragmentBinding
    lateinit var adapter: TrainingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()

        myDockActivity?.getTrainingViewModel()?.apiListener = this

        getTraining()

        return binding.root
    }

    private fun initView() {
        binding = TrainingFragmentBinding.inflate(layoutInflater)
    }

    private fun getTraining() {
        myDockActivity?.showProgressIndicator()
        myDockActivity?.getTrainingViewModel()?.getTrainings()
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.TRAINING -> {
                try {
                    val trainingResponse = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, TrainingResponse::class.java)
                    if (trainingResponse?.training != null) {
                        adapter = TrainingListAdapter(requireContext(), this)
                        adapter.setList(trainingResponse.training)
                        binding.trainingRv.adapter = adapter
                    }
                } catch (e: Exception) {
                    myDockActivity?.showErrorMessage(getString(R.string.something_went_wrong))
                }
            }
        }
    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.TRAINING_DETAILS, data as Parcelable)
        navigateToFragment(R.id.action_nav_training_to_nav_comprehensive, bundle)
    }

}