package com.example.abl.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.abl.fragment.AllFragment
import com.example.abl.fragment.MarketingCollateralItemFragment
import com.example.abl.model.CompanyLeadStatu
import com.example.abl.model.MarketingCollateralItem
import com.example.abl.model.MarketingCollateralResponse
import com.example.abl.utils.GsonFactory

class MarketingViewPagerAdapter (fm: FragmentManager, numberOfTabs: Int, data: List<MarketingCollateralResponse>): FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    var fragment: Fragment? = null
    var noOfTabs: Int = numberOfTabs
    var data: List<MarketingCollateralResponse> = data

    override fun getItem(position: Int): Fragment {
        return MarketingCollateralItemFragment.newInstance(GsonFactory.getConfiguredGson()?.toJson(data[position])!!)
    }

    override fun getCount(): Int {
        return noOfTabs
    }
}