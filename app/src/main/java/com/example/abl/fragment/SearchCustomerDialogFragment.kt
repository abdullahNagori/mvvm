package com.example.abl.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.abl.R
import com.example.abl.base.BaseDialogFragment
import com.example.abl.databinding.SearchCustomerDialogFragmentBinding

class SearchCustomerDialogFragment : BaseDialogFragment() {


    lateinit var binding: SearchCustomerDialogFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()

        return binding.root
    }

    private fun initView(){
        binding = SearchCustomerDialogFragmentBinding.inflate(layoutInflater)
        binding.closeAction.setOnClickListener {
            dialog!!.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }



}