package com.uhfsolutions.abl.fragment.salesManagement

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uhfsolutions.abl.adapter.VisitsLogsAdapter
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.base.ClickListner
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.databinding.VisitLogsFragmentBinding
import com.uhfsolutions.abl.fragment.customerDetail.PreviousVisitFragment
import com.uhfsolutions.abl.model.checkin.CheckinModel

class VisitLogsFragment : BaseDockFragment(), ClickListner {

    companion object {
        fun newInstance() = PreviousVisitFragment()
    }

    lateinit var binding: VisitLogsFragmentBinding
    lateinit var logList: List<CheckinModel>
    lateinit var adapter: VisitsLogsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        initRecyclerView()
        setData()

        return binding.root
    }

    private fun initView() {
        binding = VisitLogsFragmentBinding.inflate(layoutInflater)
    }

    private fun initRecyclerView(){
        adapter = VisitsLogsAdapter(requireContext(), this)
        binding.visit.adapter = adapter
    }

    private fun setData() {
        logList = roomHelper.getCheckInCallData(Constants.VISIT)
        binding.totalCustomers.text = logList.size.toString()
        if (logList.isNotEmpty()) {
            adapter.setList(logList)
            adapter.notifyDataSetChanged()
        }else {
            binding.dataNotFound.root.visibility = View.VISIBLE
        }

    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        val bundle = Bundle()
        val logDetailsFragment = VisitLogDetailFragment()
        bundle.putParcelable(Constants.VISITS_LOGS_DETAILS, data as Parcelable)
        logDetailsFragment.arguments = bundle
        logDetailsFragment.show(childFragmentManager, "visits")
    }





}