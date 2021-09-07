package com.example.abl.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.base.BaseDockFragment
import com.example.abl.databinding.MtdFragmentBinding

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