package com.uhfsolutions.abl.fragment.trainingAndQuiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.adapter.QuizAnswerAdapter
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.databinding.QuizAnswerDetailFragmentBinding

class QuizAnswerDetailFragment : BaseDockFragment() {

    lateinit var binding: QuizAnswerDetailFragmentBinding
    lateinit var quizadapter: QuizAnswerAdapter
    private  var mCurrentPage : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()

        return binding.root
    }

    private fun initView() {
        binding = QuizAnswerDetailFragmentBinding.inflate(layoutInflater)
        quizadapter = QuizAnswerAdapter(requireContext())
        binding.viewPager.adapter = quizadapter
        binding.viewPager.addOnPageChangeListener(onPageChangeListener)

        binding.btnNext.setOnClickListener {
            binding.viewPager.currentItem = mCurrentPage + 1
        }
    }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }
        override fun onPageSelected(position: Int) {

            mCurrentPage = position

            if (position == 0)
            {
                binding.btnNext.text = "Next"
//                binding.btnNext.setOnClickListener {
//                    val intent = Intent(this@OnBoardingActivity, MainActivity::class.java)
//                    startActivity(intent)
//                }
            }
            else if (position  == 1)
            {
                binding.btnNext.text = "Finish"
                binding.btnNext.setOnClickListener {
                    navigateToFragment(R.id.action_nav_answer_detail_to_nav_home)
                }
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }
}