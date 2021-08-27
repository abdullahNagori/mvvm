package com.example.abl.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.base.BaseDockFragment
import com.example.abl.databinding.ComprehensiveTrainingFragmentBinding
import com.example.abl.databinding.TrainingFragmentBinding

class ComprehensiveTrainingFragment : BaseDockFragment() {

    lateinit var binding: ComprehensiveTrainingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()

        binding.attemptQuiz.setOnClickListener {
            navigateToFragment(R.id.action_nav_comprehensive_to_nav_training_quiz)
        }
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
        binding = ComprehensiveTrainingFragmentBinding.inflate(layoutInflater)
    }
}