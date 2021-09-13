package com.example.abl.fragment.customerDetail

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.adapter.PreviousVisitAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.PreviousVisitFragmentBinding
import com.example.abl.model.addLead.DynamicLeadsItem
import com.example.abl.model.previousVisits.GetPreviousVisit


class PreviousVisitFragment : BaseDockFragment(), ClickListner {

    companion object {
        fun newInstance() = PreviousVisitFragment()
    }

    lateinit var binding: PreviousVisitFragmentBinding
    lateinit var adapter: PreviousVisitAdapter
    lateinit var previousList: List<GetPreviousVisit>
    lateinit var dynamicLeadsItem: DynamicLeadsItem


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        return binding.root
    }

    private fun initView() {
        binding = PreviousVisitFragmentBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        previousList = dynamicLeadsItem.get_previous_visit
        binding.totalCustomers.text = previousList.size.toString()
        adapter = PreviousVisitAdapter(requireContext(), this)
        if (previousList.isNotEmpty()){
            adapter.setList(previousList)
            binding.previous.adapter = adapter
        }else {
            binding.dataNotFound.root.visibility = View.VISIBLE
        }


    }




    override fun <T> onClick(data: T, createNested: Boolean) {
        val logDetailsFragment = PreviousVisitDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable(Constants.PREVIOUS_LOGS_DETAILS, data as Parcelable)
        logDetailsFragment.arguments = bundle
        logDetailsFragment.show(childFragmentManager, "visits")
    }
}