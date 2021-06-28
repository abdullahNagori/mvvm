package com.example.abl.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.abl.base.BaseDialogFragment
import com.example.abl.databinding.LogDetailsFragmentBinding

class PreviousVisitDetailFragment : BaseDialogFragment() {

    companion object {
        fun newInstance() = PreviousVisitDetailFragment()
    }

    private lateinit var binding: LogDetailsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }
    private fun initView() {
        binding = LogDetailsFragmentBinding.inflate(layoutInflater)
        binding.closeAction.setOnClickListener {
            dialog!!.dismiss()
        }
    }
}