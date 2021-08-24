package com.example.abl.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.adapter.DynamicViewPagerAdapter
import com.example.abl.adapter.MarketingViewPagerAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.MarketingCollateralFragmentBinding
import com.example.abl.model.DynamicLeadsResponse
import com.example.abl.model.MarketingCollateralItem
import com.example.abl.model.MarketingCollateralResponse
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MarketingCollateralFragment : BaseDockFragment() {

    lateinit var binding: MarketingCollateralFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myDockActivity?.getUserViewModel()?.apiListener = this

        getMarketingCollateral()

        initView()

        return binding.root
    }

    private fun initView() {
        binding = MarketingCollateralFragmentBinding.inflate(layoutInflater)
    }

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun setTitle(text: String) {
        TODO("Not yet implemented")
    }

    private fun getMarketingCollateral() {
        myDockActivity?.getUserViewModel()?.getMarketingCollateral()
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.MARKETING_COLLATERAL -> {
                try {
                    Log.d("liveDataValue", liveData.value.toString())
                    val gson = Gson()
                    val listType: Type =
                        object : TypeToken<List<MarketingCollateralResponse?>?>() {}.type
                    val posts: List<MarketingCollateralResponse> =
                        gson.fromJson<List<MarketingCollateralResponse>>(liveData.value, listType)
                    setupViewPager(posts)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
        }
    }

    private fun setupViewPager(data: List<MarketingCollateralResponse>) {
        binding.viewPager.adapter = MarketingViewPagerAdapter(childFragmentManager, data.size, data)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
            tabAnimation()
        data.forEachIndexed { index, element ->
            binding.tabLayout.getTabAt(index)?.text = element.section
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                //binding.viewPager.currentItem = p0!!.position
                if (tab == null)
                    return
                val position = tab.position

                binding.tabLayout.getTabAt(position)?.view?.startAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.zoomin
                    )
                )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab == null)
                    return
                val position = tab.position
                binding.tabLayout.getTabAt(position)?.view?.startAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.zoomout
                    )
                )
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
        })
    }

    private fun tabAnimation() {
        binding.tabLayout.getTabAt(1)?.view?.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.zoomout
            )
        )
        binding.tabLayout.getTabAt(2)?.view?.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.zoomout
            )
        )
    }
}