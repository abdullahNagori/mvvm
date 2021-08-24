package com.example.abl.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.viewpager.widget.ViewPager
import com.example.abl.R
import com.example.abl.adapter.ViewPagerAdapter
import com.example.abl.constant.Constants
import com.example.abl.databinding.CustomerDetailsFragmentBinding
import com.example.abl.model.DynamicLeadsItem
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.portfolio_fragment.*


class CustomerDetailsFragment : Fragment() {

    lateinit var binding: CustomerDetailsFragmentBinding
    private lateinit var dynamicLeadsItem: DynamicLeadsItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()
        setUpViewPager()
        tabAnimation()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null)
                    return
                val position = tab.position
                tab_layout.getTabAt(position)?.view?.startAnimation(
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
                tab_layout.getTabAt(position)?.view?.startAnimation(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.zoomout
                    )
                )
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        return binding.root
    }

    private fun initView(){
        binding = CustomerDetailsFragmentBinding.inflate(layoutInflater)
        dynamicLeadsItem = arguments?.getParcelable(Constants.LEAD_DATA)!!
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
        binding.tabLayout.getTabAt(3)?.view?.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.zoomout
            )
        )
        binding.tabLayout.getTabAt(4)?.view?.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.zoomout
            )
        )
        binding.tabLayout.getTabAt(5)?.view?.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.zoomout
            )
        )
    }

    private fun setUpViewPager(){
        binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager)
        addTabs(binding.viewPager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun addTabs(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)

        val customerInfoFragment = CustomerInfoFragment()
        customerInfoFragment.dynamicLeadsItem = dynamicLeadsItem

        val productsFragment = ProductsFragment()
        productsFragment.dynamicLeadsItem = dynamicLeadsItem

        val previousVisitFragment = PreviousVisitFragment()
        previousVisitFragment.dynamicLeadsItem = dynamicLeadsItem

        val calculatorFragment = CalculatorFragment()
        calculatorFragment.dynamicLeadsItem = dynamicLeadsItem

        val meetingQRFragment = MeetingQRFragment()
        meetingQRFragment.dynamicLeadsItem = dynamicLeadsItem

        val updateLocationFragment = UpdateLocationFragment()
        updateLocationFragment.dynamicLeadsItem = dynamicLeadsItem

        adapter.addFrag(customerInfoFragment, Constants.CUSTOMER_INFO)
        adapter.addFrag(productsFragment, Constants.PRODUCT)
        adapter.addFrag(previousVisitFragment, Constants.PREVIOUS_VISIT)
        adapter.addFrag(calculatorFragment, Constants.CALCULATOR)
        adapter.addFrag(meetingQRFragment, Constants.MEETING_QR)
        adapter.addFrag(updateLocationFragment, Constants.UPDATE_LOCATION)
        viewPager.adapter = adapter
    }
}