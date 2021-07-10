package com.example.abl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.base.BaseFragment
import com.example.abl.base.ClickListner
import com.example.abl.databinding.PreviousVisitFragmentBinding
import com.example.abl.model.DynamicLeadsItem


class PreviousVisitFragment : BaseFragment(), ClickListner {

    companion object {
        fun newInstance() = PreviousVisitFragment()
    }

    lateinit var dynamicLeadsItem: DynamicLeadsItem
    lateinit var binding: PreviousVisitFragmentBinding
    //val dataList = ArrayList<PreviousVisit>()
   // lateinit var adapter: PreviousVisitAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()

     //   dummyData(binding.visit)

        return binding.root
    }

    private fun initView() {
        binding = PreviousVisitFragmentBinding.inflate(layoutInflater)
    }

//    private fun dummyData(ltd: RecyclerView){
//
//        dataList.clear()
//        val nameOne = "Visit"
//        val numberOne = "2020-08-01"
//        val accountOne = "Follow up"
//        val leadStatusOne = "Uzair"
//
//        val nameTwo = "Call"
//        val numberTwo = "2020-08-20"
//        val accountTwo = "Follow up"
//        val leadStatusTwo = "Abdullah"
//
//        val objectOne = PreviousVisit(nameOne, numberOne, accountOne, leadStatusOne)
//        val objectTwo = PreviousVisit(nameTwo, numberTwo, accountTwo, leadStatusTwo)
//        dataList.add(objectOne)
//        dataList.add(objectTwo)
//
//        if (dataList.isEmpty())
//        {
//            Log.i("list", "null")
//        }
//        else
//        {
//            adapter = PreviousVisitAdapter(requireContext(), this)
//            adapter.setList(dataList)
//            adapter.notifyDataSetChanged()
//            ltd.adapter = adapter
//        }
//
//    }



    override fun <T> onClick(data: T, createNested: Boolean) {
//        val logDetailsFragment = PreviousVisitDetailFragment()
//        logDetailsFragment.show(childFragmentManager, "visits")

    }
}