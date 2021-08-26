package com.example.abl.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.base.BaseDockFragment
import com.example.abl.databinding.CalculatorFragmentBinding
import com.example.abl.databinding.PreviousVisitFragmentBinding
import com.example.abl.model.DynamicLeadsItem


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

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        if (args != null) {
            MainActivity.navController.navigate(id, args)
            return
        }
        MainActivity.navController.navigate(id)
    }

    override fun setTitle(text: String) {
        TODO("Not yet implemented")
    }

    private fun initView() {
        binding = CalculatorFragmentBinding.inflate(layoutInflater)
        binding.taxBtn.setOnClickListener {
            //navigateToFragment(R.id.action_customerDetails_to_tax_calculator)
        }
    }

}