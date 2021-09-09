package com.example.abl.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deepakkumardk.kontactpickerlib.util.log
import com.example.abl.adapter.VisitsLogsAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.VisitLogsFragmentBinding
import com.example.abl.model.checkin.CheckinModel

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