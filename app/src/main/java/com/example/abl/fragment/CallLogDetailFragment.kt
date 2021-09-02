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
import com.example.abl.databinding.CallLogDetailFragmentBinding
import com.example.abl.model.CheckinModel
import com.example.abl.model.DynamicLeadsItem

class CallLogDetailFragment : BaseDialogFragment() {

    lateinit var binding: CallLogDetailFragmentBinding
    lateinit var customer: CheckinModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()

        arguments?.getParcelable<CheckinModel>(Constants.CALL_LOGS_DETAILS).let {
            it?.let { it1 ->
                this.customer = it1
                setData()
            }
        }

        return binding.root
    }

    private fun setData() {
        binding.accountTitle.text = customer.customer_name
        binding.accountNum.text = customer.account_num
        binding.contactNum.text = customer.mobile_phone_number
        binding.status.text = customer.visit_status
        binding.remarks.text = customer.comment
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }
    private fun initView() {
        binding = CallLogDetailFragmentBinding.inflate(layoutInflater)
        binding.closeAction.setOnClickListener {
            dialog!!.dismiss()
        }
    }


}