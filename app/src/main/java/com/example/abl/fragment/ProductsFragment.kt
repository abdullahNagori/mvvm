package com.example.abl.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.databinding.CustomerInfoFragmentBinding
import com.example.abl.databinding.ProductsFragmentBinding
import com.example.abl.model.DynamicLeadsItem


class ProductsFragment : Fragment() {

    companion object {
        fun newInstance() = ProductsFragment()
    }

    lateinit var dynamicLeadsItem: DynamicLeadsItem
    lateinit var binding: ProductsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()

        return binding.root
    }

    private fun initView() {
        binding = ProductsFragmentBinding.inflate(layoutInflater)
    }

}