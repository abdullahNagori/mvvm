package com.uhfsolutions.abl.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.uhfsolutions.abl.fragment.marketingCollateral.MarketingCollateralItemFragment
import com.uhfsolutions.abl.model.marketingCollateral.MarketingCollateralResponse
import com.uhfsolutions.abl.utils.GsonFactory

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