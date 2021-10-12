package com.uhfsolutions.abl.fragment.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.databinding.MtdFragmentBinding

class MTDFragment : BaseDockFragment() {

    lateinit var binding: MtdFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()
        return binding.root
    }

    private fun initView() {
        binding = MtdFragmentBinding.inflate(layoutInflater)

        binding.dataNotFound.root.visibility = View.VISIBLE
    }
}