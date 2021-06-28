package com.example.abl.fragment

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.base.BaseFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.AddFragmentBinding

class AddLeadFragment : BaseFragment() {

    lateinit var binding: AddFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        additionalViewVisibility()

        binding.visitLead.setOnClickListener {
            auth()
        }

        return binding.root
    }

    private fun initView() {
        binding = AddFragmentBinding.inflate(layoutInflater)
    }

    private fun auth() {

        when {
            isEmpty(binding.customerName.text.toString()) -> { showBanner(getString(R.string.error_empty_customer), Constants.ERROR) }
            isEmpty(binding.contactNum.text.toString()) -> { showBanner(getString(R.string.error_empty_contact), Constants.ERROR) }
            isEmpty(binding.companyName.text.toString()) -> { showBanner(getString(R.string.error_empty_company), Constants.ERROR) }
            isEmpty(binding.address.text.toString()) -> { showBanner(getString(R.string.error_empty_address), Constants.ERROR) }
            else ->
                saveData()
        }

    }

    private fun additionalViewVisibility() {
        binding.AdView.setOnClickListener {
            if (binding.llAdditionalInformation.visibility == View.VISIBLE){
                binding.llAdditionalInformation.visibility = View.GONE
                binding.plus.text = "+"
            }else {
                binding.llAdditionalInformation.visibility = View.VISIBLE
                binding.plus.text = "-"
            }
        }

    }

    private fun saveData(){
//        val bundle = Bundle()
//        bundle.putParcelable(Constant.LEAD_DATA)
        navigateToFragment(R.id.action_nav_visit_to_checkInFormFragment)
    }
}