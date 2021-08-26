package com.example.abl.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.adapter.PreviousVisitAdapter
import com.example.abl.adapter.UserTrackingAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.databinding.UserTrackingFragmentBinding
import com.example.abl.model.GetPreviousVisit
import com.example.abl.model.UserLocation

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
        }
        return binding.root
    }

    private fun initView() {
        binding = UserTrackingFragmentBinding.inflate(layoutInflater)
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

    private fun initRecyclerView(){
        adapter = UserTrackingAdapter(requireContext(), this)
        adapter.setList(roomHelper.getUserLocation())
        binding.userList.adapter = adapter

    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        TODO("Not yet implemented")
    }
}