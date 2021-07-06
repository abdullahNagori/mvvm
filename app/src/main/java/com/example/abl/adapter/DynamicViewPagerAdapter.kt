package com.example.abl.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.abl.fragment.AllFragment

class DynamicViewPagerAdapter(fm: FragmentManager, numberOfTabs: Int): FragmentPagerAdapter(fm) {

    var fragment: Fragment? = null
    var noOfTabs: Int = numberOfTabs
    private val mFragmentTitleList  = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        for (i in 0..noOfTabs){
            if (i == position){
                fragment = AllFragment.newInstance()
                break
            }
        }
        return fragment!!
    }


    override fun getCount(): Int {
        return noOfTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }
}