package com.uhfsolutions.abl.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uhfsolutions.abl.fragment.trainingAndQuiz.QuizFragment
import com.uhfsolutions.abl.fragment.trainingAndQuiz.TrainingQuizFragment
import com.uhfsolutions.abl.model.trainingAndQuiz.QuizResponse
import com.uhfsolutions.abl.utils.GsonFactory

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