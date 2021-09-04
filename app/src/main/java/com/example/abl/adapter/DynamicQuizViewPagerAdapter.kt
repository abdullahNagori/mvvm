package com.example.abl.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.abl.fragment.QuizFragment
import com.example.abl.fragment.TrainingQuizFragment
import com.example.abl.model.Option
import com.example.abl.model.QuizResponse
import com.example.abl.utils.GsonFactory

class DynamicQuizViewPagerAdapter(val fragmentActivity: TrainingQuizFragment, numberOfTabs: Int, data: QuizResponse): FragmentStateAdapter(fragmentActivity) {

    var fragment: Fragment? = null
    var noOfTabs: Int = numberOfTabs
    var data: QuizResponse = data

    override fun getItemCount(): Int {
        return noOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        return QuizFragment.newInstance(GsonFactory.getConfiguredGson()?.toJson(data.quiz[0].questions[position])!!,fragmentActivity)
    }
}