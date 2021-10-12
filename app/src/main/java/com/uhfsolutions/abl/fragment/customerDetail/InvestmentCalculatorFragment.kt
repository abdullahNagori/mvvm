package com.uhfsolutions.abl.fragment.customerDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.databinding.InvestmentCalculatorFragmentBinding

class InvestmentCalculatorFragment : BaseDockFragment() {

    lateinit var binding: InvestmentCalculatorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()

        return binding.root
    }

    private fun initView() {
        binding = InvestmentCalculatorFragmentBinding.inflate(layoutInflater)
    }




}