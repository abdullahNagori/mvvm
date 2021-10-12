package com.uhfsolutions.abl.fragment.portfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uhfsolutions.abl.databinding.YtdFragmentBinding

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