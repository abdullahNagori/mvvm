package com.example.abl.fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.adapter.CallLogsAdapter
import com.example.abl.adapter.VisitsLogsAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.PreviousVisitFragmentBinding
import com.example.abl.databinding.VisitLogsFragmentBinding
import com.example.abl.model.CheckinModel

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
        adapter.setList(logList)
        adapter.notifyDataSetChanged()
    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        val bundle = Bundle()
        val logDetailsFragment = VisitLogDetailFragment()
        bundle.putParcelable(Constants.VISITS_LOGS_DETAILS, data as Parcelable)
        logDetailsFragment.show(childFragmentManager, "visits")
    }

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun setTitle(text: String) {
        TODO("Not yet implemented")
    }

}