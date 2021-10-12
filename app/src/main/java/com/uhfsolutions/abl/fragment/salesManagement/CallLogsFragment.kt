package com.uhfsolutions.abl.fragment.salesManagement

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uhfsolutions.abl.adapter.CallLogsAdapter
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.base.ClickListner
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.databinding.CallLogsFragmentBinding
import com.uhfsolutions.abl.model.checkin.CheckinModel

class CallLogsFragment : BaseDockFragment(), ClickListner {

    companion object {
        fun newInstance() = CallLogsFragment()
    }

    lateinit var binding: CallLogsFragmentBinding
    lateinit var adapter: CallLogsAdapter
    lateinit var logList: List<CheckinModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        logList = roomHelper.getCheckInCallData(Constants.CALL)
        initView()
        initRecyclerView()

        binding.search.setOnClickListener {
            showCustomerDialog()
        }

        binding.txtTotalCustomers.text = logList.size.toString()
        binding.filter.setOnClickListener {
            showHideFilter()
        }

        return binding.root
    }





    private fun initView(){
        binding = CallLogsFragmentBinding.inflate(layoutInflater)
    }

    private fun showCustomerDialog(){
        val callLogSearchDialogFragment = CallLogSearchFragment()
        callLogSearchDialogFragment.show(childFragmentManager, "visits")
    }

    private fun initRecyclerView(){
        adapter = CallLogsAdapter(requireContext(), this)
        if (logList.isNotEmpty()){
            adapter.setList(logList)
            binding.logs.adapter = adapter
        }else {
            binding.dataNotFound.root.visibility = View.VISIBLE
        }

    }
    override fun <T> onClick(data: T, createNested: Boolean) {

        val logDetailsFragment = CallLogDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable(Constants.CALL_LOGS_DETAILS, data as Parcelable)
        logDetailsFragment.arguments = bundle
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