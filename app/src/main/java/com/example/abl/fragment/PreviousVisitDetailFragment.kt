package com.example.abl.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.abl.base.BaseDialogFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.LogDetailsFragmentBinding
import com.example.abl.model.checkin.CheckinModel
import com.example.abl.model.previousVisits.GetPreviousVisit

class PreviousVisitDetailFragment : BaseDialogFragment() {

    companion object {
        fun newInstance() = PreviousVisitDetailFragment()
    }

    private lateinit var binding: LogDetailsFragmentBinding
    lateinit var previousList: GetPreviousVisit


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()

        arguments?.getParcelable<GetPreviousVisit>(Constants.PREVIOUS_LOGS_DETAILS).let {
            it?.let { it1 ->
                this.previousList = it1
                setData()
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    private fun setData() {
        binding.status.text = previousList.lead_status_name
//        binding.accountNum.text = customer.account_num
//        binding.contactNum.text = customer.mobile_phone_number
//        binding.status.text = customer.visit_status
//        binding.remarks.text = customer.comment
    }

    private fun initView() {
        binding = LogDetailsFragmentBinding.inflate(layoutInflater)
        binding.closeAction.setOnClickListener {
            dialog!!.dismiss()
        }
    }
}