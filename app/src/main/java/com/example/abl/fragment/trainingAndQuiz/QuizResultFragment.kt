package com.example.abl.fragment.trainingAndQuiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.QuizResultFragmentBinding
import com.example.abl.model.trainingAndQuiz.Question

class QuizResultFragment : BaseDockFragment() {

    lateinit var binding: QuizResultFragmentBinding
    lateinit var question: ArrayList<Question>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()

        arguments?.getParcelableArrayList<Question>(Constants.QUESTION_LIST).let {
            if (it != null) {
                question = it
            }
            binding.totalQuestions.text = question.size.toString()
        }

        binding.btnViewAnswer.setOnClickListener {
            navigateToFragment(R.id.action_result_quiz_to_nav_home)
        }
        return binding.root
    }







    private fun initView() {
        binding = QuizResultFragmentBinding.inflate(layoutInflater)
    }
}