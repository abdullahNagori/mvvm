package com.example.abl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.adapter.CustomerAdapter
import com.example.abl.adapter.PreviousVisitAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.BaseFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.PreviousVisitFragmentBinding
import com.example.abl.model.DynamicLeadsItem
import com.example.abl.model.GetPreviousVisit
import kotlinx.android.synthetic.main.notification_fragment.*


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
        adapter = PreviousVisitAdapter(requireContext(), this)
        adapter.setList(previousList)
        binding.previous.adapter = adapter

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

    override fun <T> onClick(data: T, createNested: Boolean) {
//        val logDetailsFragment = PreviousVisitDetailFragment()
//        logDetailsFragment.show(childFragmentManager, "visits")
    }
}