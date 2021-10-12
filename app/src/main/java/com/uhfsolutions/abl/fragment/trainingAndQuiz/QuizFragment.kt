package com.uhfsolutions.abl.fragment.trainingAndQuiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uhfsolutions.abl.adapter.QuizOptionsAdapter
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.base.ClickListner
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.databinding.QuizFragmentBinding
import com.uhfsolutions.abl.model.trainingAndQuiz.Option
import com.uhfsolutions.abl.model.trainingAndQuiz.Question
import com.uhfsolutions.abl.utils.GsonFactory
import kotlinx.android.synthetic.main.item_checkbox_option.view.*

class QuizFragment : BaseDockFragment(), ClickListner {

    lateinit var binding: QuizFragmentBinding
    private var question: Question? = null
    lateinit var optionAdapter: QuizOptionsAdapter

    companion object {
        lateinit var trainingQuizFragment: TrainingQuizFragment
        fun newInstance(content: String, trainingQuizFragment: TrainingQuizFragment): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putString(Constants.QUIZ_DATA, content)
            fragment.arguments = args
            Companion.trainingQuizFragment = trainingQuizFragment
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        question = GsonFactory.getConfiguredGson()?.fromJson(arguments?.getString(Constants.QUIZ_DATA), Question::class.java)
        binding.qtnNumTv.text = "Question # ${question?.record_id}"
        binding.qtnTv.text = question?.question

        question?.let { initRecyclerView(it.options) }
        return binding.root
    }

    private fun initView() {
        binding = QuizFragmentBinding.inflate(layoutInflater)
    }




    private fun initRecyclerView(optionList: List<Option>) {
        optionAdapter = QuizOptionsAdapter(requireContext(), this)
        optionAdapter.setList(optionList)
        binding.optionRv.adapter = optionAdapter
    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        optionAdapter.getAllItems().forEach{
            if((data as Option).option != it.optionOne.text && it.optionOne.isChecked){
                it.optionOne.isChecked = false
            }else if((data as Option).option == it.optionOne.text){
                it.optionOne.isChecked = true
            }
        }
        trainingQuizFragment.isChecked = true

        trainingQuizFragment.setSubmissionRequest(question!!.quiz_id,question!!.record_id,question!!.correct_ans)
    }
}