package com.example.abl.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.adapter.CallLogsAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.CallLogsFragmentBinding
import com.example.abl.model.CheckinModel

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

        binding.filter.setOnClickListener {
            showHideFilter()
        }

        return binding.root
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

    private fun initView(){
        binding = CallLogsFragmentBinding.inflate(layoutInflater)
    }

    private fun showCustomerDialog(){
        val callLogSearchDialogFragment = CallLogSearchFragment()
        callLogSearchDialogFragment.show(childFragmentManager, "visits")
    }

    private fun initRecyclerView(){
        adapter = CallLogsAdapter(requireContext(), this)
        adapter.setList(logList)
        binding.logs.adapter = adapter
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