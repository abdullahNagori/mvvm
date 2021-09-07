package com.example.abl.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.adapter.CustomerAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.FollowupFragmentBinding
import com.example.abl.model.addLead.DynamicLeadsItem

class FollowupFragment : BaseDockFragment(), ClickListner {

    lateinit var binding: FollowupFragmentBinding
    lateinit var adapter: CustomerAdapter
    lateinit var followupList: List<DynamicLeadsItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        followupList = roomHelper.getFollowupData()
        initView()
        initRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pullToRefresh.setOnRefreshListener {
            initRecyclerView()
        }

    }





    private fun initView(){
        binding = FollowupFragmentBinding.inflate(layoutInflater)
        binding.totalCustomers.text = followupList.size.toString()
    }

    private fun initRecyclerView(){
        adapter = CustomerAdapter(requireContext(), this)
        adapter.setList(followupList)
        binding.followupRv.adapter = adapter
    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.CUSTOMER_DATA, data as Parcelable)
        navigateToFragment(R.id.customerDetailsFragment, bundle)
    }
}