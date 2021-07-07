package com.example.abl.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.abl.fragment.AllFragment
import com.example.abl.model.DynamicLeadsResponse
import com.example.abl.utils.GsonFactory

class DynamicViewPagerAdapter(fm: FragmentManager, numberOfTabs: Int, data: List<DynamicLeadsResponse>): FragmentPagerAdapter(fm) {

    var fragment: Fragment? = null
    var noOfTabs: Int = numberOfTabs
    var data: List<DynamicLeadsResponse> = data

    private val mFragmentTitleList  = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return AllFragment.newInstance(GsonFactory.getConfiguredGson()?.toJson(data[position])!!)
    }


    override fun getCount(): Int {
        return noOfTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }
}