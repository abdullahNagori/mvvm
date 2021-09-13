package com.example.abl.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.abl.constant.Constants
import com.example.abl.fragment.leadManagement.AllFragment
import com.example.abl.model.lov.CompanyLeadStatu

class DynamicViewPagerAdapter(fm: FragmentManager, numberOfTabs: Int, data: List<CompanyLeadStatu>, leadSourceData: String): FragmentPagerAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var fragment: Fragment? = null
    var noOfTabs: Int = numberOfTabs
    var leadSourceData = leadSourceData
    var data: List<CompanyLeadStatu> = data

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString(Constants.LEAD_SOURCE_DATA, leadSourceData)
        bundle.putString(Constants.LEAD_STATUS_NAME, data[position].name)
        val fragment = AllFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return noOfTabs
    }
}