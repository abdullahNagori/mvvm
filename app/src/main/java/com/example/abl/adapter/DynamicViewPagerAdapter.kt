package com.example.abl.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.abl.fragment.AllFragment

class DynamicViewPagerAdapter(fm: FragmentManager, numberOfTabs: Int, title: String): FragmentPagerAdapter(fm) {

    var fragment: Fragment? = null
    var noOfTabs: Int = numberOfTabs
    var title: String = title

    override fun getItem(position: Int): Fragment {
        for (i in 0..noOfTabs){
            if (i == position){
                fragment = AllFragment.newInstance(title = title)
                break
            }
        }
        return fragment!!
    }

//    fun addFrag(fragment: Fragment, title: String) {
//        mFragmentList.add(fragment)
//        mFragmentTitleList.add(title)
//    }
    override fun getCount(): Int {
        return noOfTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }
}