package com.example.abl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.viewpager.widget.ViewPager
import com.example.abl.R
import com.example.abl.adapter.ViewPagerAdapter
import com.example.abl.base.BaseFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.PortfolioFragmentBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.portfolio_fragment.*


class PortfolioFragment : BaseFragment() {

    lateinit var binding: PortfolioFragmentBinding

    companion object {
        fun newInstance() = PortfolioFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
    }

    private fun initView() {
        binding = PortfolioFragmentBinding.inflate(layoutInflater)
        blockLeftDrawer()
    }
    private fun setUpViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager)
        addTabs(binding.viewPager)
        tab_layout.setupWithViewPager(binding.viewPager)
    }
    private fun addTabs(viewPager: ViewPager) {

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFrag(LTDFragment(), Constants.LTD)
        adapter.addFrag(MTDFragment(), Constants.MTD)
        adapter.addFrag(YTDFragment(), Constants.YTD)
        viewPager.adapter = adapter
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