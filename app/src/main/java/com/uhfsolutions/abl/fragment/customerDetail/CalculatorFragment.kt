package com.uhfsolutions.abl.fragment.customerDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.databinding.CalculatorFragmentBinding
import com.uhfsolutions.abl.model.addLead.DynamicLeadsItem


class CalculatorFragment : BaseDockFragment() {

    companion object {
        fun newInstance() = CalculatorFragment()
    }

    lateinit var dynamicLeadsItem: DynamicLeadsItem
    lateinit var binding: CalculatorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()

        return binding.root
    }







    private fun initView() {
        binding = CalculatorFragmentBinding.inflate(layoutInflater)
        binding.taxBtn.setOnClickListener {
            //navigateToFragment(R.id.action_customerDetails_to_tax_calculator)
        }
    }

}