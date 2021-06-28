package com.example.abl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.adapter.CallLogsAdapter
import com.example.abl.base.BaseFragment
import com.example.abl.base.ClickListner
import com.example.abl.databinding.CallLogsFragmentBinding


data class CallLogsList(

    var time: String,
    var mobile: String,
    var name: String,
    var status: String,
    var ampm: String
        )

class CallLogsFragment : BaseFragment(), ClickListner {

    companion object {
        fun newInstance() = CallLogsFragment()
    }

    lateinit var binding: CallLogsFragmentBinding
    val dataList = ArrayList<CallLogsList>()
    lateinit var adapter: CallLogsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()

        binding.search.setOnClickListener {
            showCustomerDialog()
        }

        binding.filter.setOnClickListener {
            showHideFilter()
        }

        dummyData(binding.logs)
        return binding.root
    }

    private fun initView(){
        binding = CallLogsFragmentBinding.inflate(layoutInflater)
    }

    private fun dummyData(ltd: RecyclerView){

        dataList.clear()
        val nameOne = "12:30"
        val numberOne = "0312XXXXXXX"
        val accountOne = "Aftab Alam"
        val leadStatusOne = "Followup"
        val am = "AM"

        val nameTwo = "01:18"
        val numberTwo = "0345XXXXXXX"
        val accountTwo = "Chota Shakeel"
        val leadStatusTwo = "Closed"
        val pm = "PM"

        val objectOne = CallLogsList(nameOne, numberOne, accountOne, leadStatusOne, am)
        val objectTwo = CallLogsList(nameTwo, numberTwo, accountTwo, leadStatusTwo, pm)
        dataList.add(objectOne)
        dataList.add(objectTwo)

        if (dataList.isEmpty())
        {
            Log.i("list", "null")
        }
        else
        {
            adapter = CallLogsAdapter(requireContext(), this)
            adapter.setList(dataList)
            adapter.notifyDataSetChanged()
            ltd.adapter = adapter
        }

    }

    private fun showCustomerDialog(){
        val callLogSearchDialogFragment = CallLogSearchFragment()
        callLogSearchDialogFragment.show(childFragmentManager, "visits")
    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        val logDetailsFragment = CallLogDetailFragment()
        logDetailsFragment.show(childFragmentManager, "visits")
    }

    private fun showHideFilter(){
        if(binding.filterLayout.root.visibility == View.VISIBLE){
            binding.filterLayout.root.visibility = View.GONE
        }else {
            binding.filterLayout.root.visibility = View.VISIBLE
        }
    }
}