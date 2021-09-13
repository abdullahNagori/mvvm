package com.example.abl.fragment.portfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.databinding.MtdFragmentBinding
import com.example.abl.databinding.YtdFragmentBinding

class YTDFragment : Fragment() {

    lateinit var binding: YtdFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()

        return binding.root
    }


    private fun initView() {
        binding = YtdFragmentBinding.inflate(layoutInflater)
        binding.dataNotFound.root.visibility = View.VISIBLE
    }
}