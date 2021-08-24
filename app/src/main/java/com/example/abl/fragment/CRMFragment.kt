package com.example.abl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.viewpager.widget.ViewPager
import com.example.abl.R
import com.example.abl.adapter.DynamicViewPagerAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.FragmentCrmBinding
import com.example.abl.model.DynamicLeadsItem
import com.example.abl.model.DynamicLeadsResponse
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_crm.*
import kotlinx.android.synthetic.main.portfolio_fragment.*
import kotlinx.android.synthetic.main.portfolio_fragment.tab_layout
import java.lang.reflect.Type
import java.util.ArrayList


class CRMFragment : BaseDockFragment() {

    lateinit var binding: FragmentCrmBinding

    // lateinit var list: List<DynamicLeadsItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myDockActivity?.getUserViewModel()?.apiListener = this
        binding = FragmentCrmBinding.inflate(layoutInflater)
        //getDynamicData("")


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
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

    override fun <T> initiateListArrayAdapter(list: List<T>): ArrayAdapter<T> {
        TODO("Not yet implemented")
    }

    private fun getDynamicData(token: String){
        myDockActivity?.getUserViewModel()?.getDynamicLeads(token)
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.GET_DYNAMIC_LEADS -> {
                try {
                    Log.d("liveDataValue", liveData.value.toString())
                    val gson = Gson()
                    val listType: Type = object : TypeToken<List<DynamicLeadsResponse?>?>() {}.type
                    val posts: List<DynamicLeadsResponse> =
                        gson.fromJson<List<DynamicLeadsResponse>>(liveData.value, listType)
                    //setupViewPager(posts)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
        }
    }

    private fun setupViewPager() {
       // val leadStatusArray = sharedPrefManager.getLeadStatus()
        val leads = roomHelper.getLeadsStatus()
        Log.i("xxLeads", leads.toString())
        binding.viewPager.offscreenPageLimit = 30
        if (leads != null) {
            binding.viewPager.adapter = DynamicViewPagerAdapter(childFragmentManager, leads.size, leads)
            binding.tabLayout.setupWithViewPager(binding.viewPager)
            leads.forEachIndexed { index, element ->
                binding.tabLayout.getTabAt(index)?.text = element.name
            }

            binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    //binding.viewPager.currentItem = p0!!.position
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

                override fun onTabReselected(p0: TabLayout.Tab?) {
                }
            })
        }
    }
/*
//    private fun setupViewPager(data: List<DynamicLeadsResponse>){
//        binding.viewPager.adapter = DynamicViewPagerAdapter(childFragmentManager, data.size, data)
//        binding.tabLayout.setupWithViewPager(binding.viewPager)
//        data.forEachIndexed { index, element ->
//            binding.tabLayout.getTabAt(index)?.text = element.section
//        }
//
//        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                //binding.viewPager.currentItem = p0!!.position
//                if (tab == null)
//                    return
//                val position = tab.position
//
//                tab_layout.getTabAt(position)?.view?.startAnimation(
//                    AnimationUtils.loadAnimation(
//                        context,
//                        R.anim.zoomin
//                    )
//                )
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                if (tab == null)
//                    return
//                val position = tab.position
//                tab_layout.getTabAt(position)?.view?.startAnimation(
//                    AnimationUtils.loadAnimation(
//                        context,
//                        R.anim.zoomout
//                    )
//                )
//            }
//
//            override fun onTabReselected(p0: TabLayout.Tab?) {
//            }
//        })
//    }
 */

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