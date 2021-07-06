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
import com.example.abl.model.DynamicLeadsResponse
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_crm.*
import kotlinx.android.synthetic.main.portfolio_fragment.*
import kotlinx.android.synthetic.main.portfolio_fragment.tab_layout
import java.lang.reflect.Type


class CRMFragment : BaseDockFragment() {

    lateinit var binding: FragmentCrmBinding
    lateinit var pagerAdapter: DynamicViewPagerAdapter

    // lateinit var list: List<DynamicLeadsItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this
      //  viewPager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager()
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

    private fun initView(){
        binding = FragmentCrmBinding.inflate(layoutInflater)
       // getDynamicData("Bearer "+sharedPrefManager.getToken())
        getDynamicData("Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhbGxpZWQtYmFuayIsInN1YiI6IjEiLCJpYXQiOjE2MjU1OTgzMzEsImV4cCI6MTYyNTYwMTkzMSwiZGF0YSI6eyJzdWNjZXNzIjp0cnVlLCJjb2RlIjowLCJtZXNzYWdlIjoiTG9naW4gU3VjY2Vzc2Z1bGx5Iiwic2lkIjoiNjYxZDVlZmUyMzQyZGYwYjA4YzE4NTJiMTMyZTFhMTciLCIyX2ZhIjoieWVzIiwidXNlcl9pZCI6IjEifX0.j4VMgc6QkiQdgdBChH3C2kmEVGV8Bb-CMFw3vFdJpOw")
    }

    private fun getDynamicData(token: String){
        myDockActivity?.getUserViewModel()?.getDynamicLeads(token)
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.GET_DYNAMIC_LEADS -> {
                try
                {
                    Log.d("liveDataValue", liveData.value.toString())
                    val gson = Gson()
                    val listType: Type = object : TypeToken<List<DynamicLeadsResponse?>?>() {}.type
                    val posts: List<DynamicLeadsResponse> = gson.fromJson<List<DynamicLeadsResponse>>(liveData.value, listType)
                    sharedPrefManager.setSection(posts.size)

                    posts.forEachIndexed { index, element ->
                        binding.tabLayout.getTabAt(index)?.text = element.section
                        Log.i("Index", index.toString())
                        Log.i("Element", element.section)
                    }

                }
                catch (e: Exception){
                    Log.d("Exception",e.message.toString())
                }
            }
            }
        }


    private fun viewPager(){

        setUpViewPager()
        tabAnimation()
        setupTabIcons()

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

    private fun setUpViewPager() {
        binding.viewPager.adapter = DynamicViewPagerAdapter(childFragmentManager,sharedPrefManager.getSection())
        addTabs(binding.viewPager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun addTabs(viewPager: ViewPager) {
        val adapter = DynamicViewPagerAdapter(childFragmentManager, sharedPrefManager.getSection())
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

    private fun setupTabIcons() {
        binding.tabLayout.getTabAt(0)?.text = "all"
        binding.tabLayout.getTabAt(1)?.text = "open"
        binding.tabLayout.getTabAt(2)?.text = "inprocess"
        binding.tabLayout.getTabAt(3)?.text = "closed"
        binding.tabLayout.getTabAt(4)?.text = "qualified"
        binding.tabLayout.getTabAt(5)?.text = "followup"
        binding.tabLayout.getTabAt(6)?.text = "not_interested"
    }
}