package com.example.abl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.abl.R

class QuizAnswerAdapter(private val mContext: Context) : PagerAdapter() {

    var questionTxt = arrayOf(
        "What is our toll free number?",
        "What is our toll free number?"
    )

    var questionNo = arrayOf(
        "Question # 1",
        "Question # 2"
    )

    override fun getCount(): Int {
        return questionTxt.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflator: LayoutInflater =
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflator.inflate(R.layout.item_answer_quiz_adapter, container, false)

        val question = view.findViewById(R.id.qtn_tv) as TextView
        val questionNumber = view.findViewById(R.id.qtn_num_tv) as TextView
        question.text = questionTxt[position]
        questionNumber.text = questionNo[position]

        container.addView(view)


        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

}