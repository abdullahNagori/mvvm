package com.example.abl.fragment.customerDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.adapter.ProductListAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.databinding.ProductsFragmentBinding
import com.example.abl.model.lov.CompanyProduct
import com.example.abl.model.addLead.DynamicLeadsItem


class ProductsFragment : BaseDockFragment(), ClickListner {

    companion object {
        fun newInstance() = ProductsFragment()
    }

    lateinit var dynamicLeadsItem: DynamicLeadsItem
    lateinit var binding: ProductsFragmentBinding
    lateinit var adapter: ProductListAdapter

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

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }





    private fun initRecyclerView(){
        adapter = ProductListAdapter(requireContext(), this)
        adapter.setList(sharedPrefManager.getCompanyProducts() as ArrayList<CompanyProduct>)
        binding.customers.adapter = adapter
    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        Log.i("xxProduct", "product")
    }
}