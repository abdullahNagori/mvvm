package com.example.abl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.adapter.ProductAdapter
import com.example.abl.base.BaseDialogFragment
import com.example.abl.base.ClickListner
import com.example.abl.databinding.FragmentProductDialogBinding
import com.example.abl.model.lov.CompanyProduct

class ProductDialogFragment(var listner: ClickListner) : BaseDialogFragment() {


    lateinit var listOfProduct : List<CompanyProduct>
    lateinit var alreadySelectedProduct : ArrayList<CompanyProduct>
    lateinit var binding : FragmentProductDialogBinding
    lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()

        if ((this::alreadySelectedProduct.isInitialized))
            initRecyerView(listOfProduct, alreadySelectedProduct)
        else {
            initRecyerView(listOfProduct, arrayListOf())
        }

        binding.btnSubmit.setOnClickListener {
            if ((this::alreadySelectedProduct.isInitialized)) {
                if (alreadySelectedProduct.isNotEmpty()) {
                    productAdapter.newProductList.addAll(alreadySelectedProduct)
                }
            }
            listner.onClick(productAdapter.newProductList)
        }
        return binding.root
    }


    private fun initView(){
        binding = FragmentProductDialogBinding.inflate(layoutInflater)
    }

    private fun initRecyerView(listOfProduct: List<CompanyProduct>, alreadySelected: ArrayList<CompanyProduct>) {
        productAdapter = ProductAdapter(requireContext(), listOfProduct, alreadySelected)
        binding.mRecyerID.adapter = productAdapter
        productAdapter.notifyDataSetChanged()
    }

}