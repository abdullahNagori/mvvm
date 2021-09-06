package com.example.abl.fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.adapter.DynamicQuizViewPagerAdapter
import com.example.abl.adapter.QuizFormAdapter
import com.example.abl.adapter.QuizOptionsAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.TrainingFragmentBinding
import com.example.abl.databinding.TrainingQuizFragmentBinding
import com.example.abl.model.*
import com.google.gson.reflect.TypeToken
import com.example.abl.utils.GsonFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_checkbox.view.*
import kotlinx.android.synthetic.main.item_checkbox_option.view.*
import kotlinx.android.synthetic.main.training_quiz_fragment.*
import org.koin.android.ext.android.bind
import java.lang.reflect.Type


class TrainingQuizFragment : BaseDockFragment() {
    lateinit var binding: TrainingQuizFragmentBinding
    lateinit var quizadapter: QuizFormAdapter
    lateinit var trainingID: String
    lateinit var questionsList: ArrayList<Question>
    var quizDetail = ArrayList<QuizDetailItem>()
    val submitQuizModel= SubmitQuizModel("", ArrayList<QuizDetailItem>())
    var isChecked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myDockActivity?.getUserViewModel()?.apiListener = this
        initView()
        arguments?.getParcelableArrayList<Material>(Constants.MATERIAL_LIST).let {
            trainingID = it?.get(0)?.training_id.toString()
        }
        getQuizData(
            GetQuizModel(
            trainingID
        )
        )

        return binding.root
    }

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        if (args != null) {
            MainActivity.navController.navigate(id, args)
            return
        }
        MainActivity.navController.navigate(id)
    }

    override fun setTitle(text: String) {
        TODO("Not yet implemented")
    }

    private fun initView() {
        binding = TrainingQuizFragmentBinding.inflate(layoutInflater)
        quizadapter = QuizFormAdapter(requireContext())
        binding.btnNext.setOnClickListener {
            if(btn_next.text.toString() =="Next" && isChecked){
                binding.viewPager.currentItem+=1
                isChecked = false
            }
            else {
                myDockActivity?.showErrorMessage(getString(R.string.error_checkbox))
            }
        }
    }

    private fun getQuizData(getQuizModel: GetQuizModel){
        myDockActivity?.getUserViewModel()?.getQuiz(getQuizModel)
    }

    private fun submitQuizData(submitQuizModel: SubmitQuizModel){
        myDockActivity?.getUserViewModel()?.submitQuiz(submitQuizModel)
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.MATERIAL -> {
                try {
                    Log.d("liveDataValue", liveData.value.toString())
                    val gson = Gson()
                    val listType: Type = object : TypeToken<QuizResponse?>() {}.type
                    val posts: QuizResponse = gson.fromJson<QuizResponse>(liveData.value, listType)
                    questionsList = posts.quiz[0].questions as ArrayList<Question>
                    setupViewPager(posts)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            Constants.SUBMIT_QUIZ -> {
                val submitQuizResponseEnt = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, GenericMsgResponse::class.java)
                if (submitQuizResponseEnt?.message?.toLowerCase()?.contains("success")!!){
                    val bundle = Bundle()
                    bundle.putParcelableArrayList(Constants.QUESTION_LIST, questionsList)
                    bundle.putParcelable(Constants.SUBMIT_QUIZ, submitQuizModel as Parcelable)
                    navigateToFragment(R.id.action_nav_training_quiz_to_nav_result_quiz, bundle)
                }
            }
        }
    }

    private fun setupViewPager(data: QuizResponse) {
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = DynamicQuizViewPagerAdapter(this, data.quiz[0].questions.size, data)
        binding.viewPager.registerOnPageChangeCallback(onPageChangeListener)

    }
    private val onPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }
        override fun onPageSelected(position: Int) {
            if (position  == binding.viewPager.adapter?.itemCount!!-1)
            {
                binding.btnNext.text = "Finish"
                binding.btnNext.setOnClickListener {
                    if (isChecked) {
                        submitQuizData(submitQuizModel)
                    }
                    else{
                        myDockActivity?.showErrorMessage(getString(R.string.error_checkbox))
                    }
                }
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }

    fun setSubmissionRequest(quizId:String,questionId:String,answer:String){
        submitQuizModel.quiz_id = quizId
        submitQuizModel.quiz_details.add(QuizDetailItem(questionId,answer))
    }
}