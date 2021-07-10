package com.example.abl.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.base.ClickListner
import com.example.abl.databinding.PreviousVisitFragmentBinding
import com.example.abl.databinding.VisitLogsFragmentBinding

class VisitLogsFragment : Fragment(), ClickListner {


    companion object {
        fun newInstance() = PreviousVisitFragment()
    }

  //  lateinit var customers: Customers
    lateinit var binding: VisitLogsFragmentBinding
//    val dataList = ArrayList<PreviousVisit>()
//    lateinit var adapter: PreviousVisitAdapter

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
        binding = VisitLogsFragmentBinding.inflate(layoutInflater)
    }

//    private fun dummyData(ltd: RecyclerView){
//
//        dataList.clear()
//        val nameOne = "Visit"
//        val numberOne = "2020-08-01"
//        val accountOne = "Follow up"
//        val leadStatusOne = "Aatir"
//
//        val nameTwo = "Call"
//        val numberTwo = "2019-08-04"
//        val accountTwo = "Follow up"
//        val leadStatusTwo = "Shakir"
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
//        val logDetailsFragment = VisitLogDetailFragment()
//        logDetailsFragment.show(childFragmentManager, "visits")

    }
}