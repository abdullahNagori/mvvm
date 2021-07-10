package com.example.abl.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.CustomerInfoFragmentBinding
import com.example.abl.model.DynamicLeadsItem

class CustomerInfoFragment : BaseDockFragment() {

    companion object {
        fun newInstance() = CustomerInfoFragment()
    }

    lateinit var dynamicLeadsItem: DynamicLeadsItem
    lateinit var binding: CustomerInfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()

        setData(dynamicLeadsItem)

        binding.call.setOnClickListener {
            callDialog(dynamicLeadsItem.type.toString(),dynamicLeadsItem.mobile_phone_number,dynamicLeadsItem)
        }

        binding.checkin.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(Constants.LEAD_DATA, dynamicLeadsItem)
            navigateToFragment(R.id.checkInFormFragment, bundle)
        }

        return binding.root
    }

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        if (args != null) {
            MainActivity.navController.navigate(id, args)
            return
        }
        MainActivity.navController.navigate(id)
    }

    override fun setTitle(text: String) {
        TODO("Not yet implemented")
    }

    private fun initView() {
        binding = CustomerInfoFragmentBinding.inflate(layoutInflater)
    }

    private fun setData(data: DynamicLeadsItem) {
        Log.d("xxData", data.toString())
        binding.accountTitle.text = data.first_name
        binding.branchCodeName.text = data.branch_name
        binding.accountType.text = data.type
        Log.i("xxName", data.first_name)
        Log.i("xxName", data.branch_name)
        Log.i("xxName", data.type)
    }
}