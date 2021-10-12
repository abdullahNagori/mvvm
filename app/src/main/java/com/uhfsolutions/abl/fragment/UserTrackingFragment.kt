package com.uhfsolutions.abl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uhfsolutions.abl.adapter.UserTrackingAdapter
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.base.ClickListner
import com.uhfsolutions.abl.databinding.UserTrackingFragmentBinding
import com.uhfsolutions.abl.model.location.UserLocation

class UserTrackingFragment : BaseDockFragment(), ClickListner {

    lateinit var binding: UserTrackingFragmentBinding
    lateinit var adapter: UserTrackingAdapter
    lateinit var userLocation: List<UserLocation>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        initRecyclerView()

        binding.pullToRefresh.setOnRefreshListener {
            initRecyclerView()
            binding.pullToRefresh.isRefreshing = false
        }
        return binding.root
    }

    private fun initView() {
        binding = UserTrackingFragmentBinding.inflate(layoutInflater)
    }





    private fun initRecyclerView(){
        adapter = UserTrackingAdapter(requireContext(), this)
        adapter.setList(roomHelper.getUserLocation())
        binding.userList.adapter = adapter

    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        TODO("Not yet implemented")
    }
}