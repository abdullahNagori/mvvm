package com.example.abl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.adapter.DynamicQuizViewPagerAdapter
import com.example.abl.adapter.QuizOptionsAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.QuizFragmentBinding
import com.example.abl.model.Option
import com.example.abl.model.Question
import com.example.abl.model.QuizDetailItem
import com.example.abl.model.SubmitQuizModel
import com.example.abl.utils.GsonFactory
import kotlinx.android.synthetic.main.comprehensive_training_fragment.*
import kotlinx.android.synthetic.main.item_checkbox.view.*
import kotlinx.android.synthetic.main.item_checkbox_option.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class QuizFragment : BaseDockFragment(), ClickListner {

    lateinit var binding: QuizFragmentBinding
    private var question: Question? = null
    lateinit var optionAdapter: QuizOptionsAdapter

    companion object {
        lateinit var trainingQuizFragment:TrainingQuizFragment
        fun newInstance(content: String, trainingQuizFragment:TrainingQuizFragment): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putString(Constants.QUIZ_DATA, content)
            fragment.arguments = args
            this.trainingQuizFragment = trainingQuizFragment
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
    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun setTitle(text: String) {
        TODO("Not yet implemented")
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
            trainingQuizFragment.isChecked = it.optionOne.isChecked
        }
        trainingQuizFragment.setSubmissionRequest(question!!.quiz_id,question!!.record_id,question!!.correct_ans)
    }
}