package com.example.abl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import com.example.abl.adapter.DynamicViewPagerAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.FragmentCrmBinding
import com.example.abl.model.DynamicLeadsResponse
import com.example.abl.utils.GsonFactory
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.properties.Delegates


class CRMFragment : BaseDockFragment() {

    lateinit var binding: FragmentCrmBinding
    var size = 0

    // lateinit var list: List<DynamicLeadsItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
      //  viewPager()
        myDockActivity?.getUserViewModel()?.apiListener = this
        return binding.root
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
        getDynamicData("Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhbGxpZWQtYmFuayIsInN1YiI6IjEiLCJpYXQiOjE2MjU1MTgzNDcsImV4cCI6MTYyNTUyMTk0NywiZGF0YSI6eyJzdWNjZXNzIjp0cnVlLCJjb2RlIjowLCJtZXNzYWdlIjoiTG9naW4gU3VjY2Vzc2Z1bGx5Iiwic2lkIjoiNDVlNzE1OTczNWYzNDM3OTg2Njk1MzVhZmM3YTdlOGUiLCIyX2ZhIjoieWVzIiwidXNlcl9pZCI6IjEifX0.HS1j8SkJoeXrJMruqSRtIxCZ8kXDiBCpbho3SlgxBYM")
    }

    private fun getDynamicData(token: String){
        myDockActivity?.getUserViewModel()?.getDynamicLeads(token)
    }

    private fun viewPager(){

        var section = 10
        for (i in 0..section){

            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Hello Fragment"))

        }

        var pagerViewAdapter = DynamicViewPagerAdapter(childFragmentManager,binding.tabLayout.tabCount,"")
        binding.viewPager.adapter = pagerViewAdapter
        binding.viewPager.adapter = pagerViewAdapter

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                binding.viewPager.currentItem = p0!!.position
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }
        })
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
                    //posts.size
                    for (secName in posts)

                            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Hello Fragment"))
                            val pagerViewAdapter = DynamicViewPagerAdapter(childFragmentManager,binding.tabLayout.tabCount, "")
                            binding.viewPager.adapter = pagerViewAdapter
                            binding.viewPager.adapter = pagerViewAdapter





                    binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
                        override fun onTabReselected(p0: TabLayout.Tab?) {

                        }

                        override fun onTabSelected(p0: TabLayout.Tab?) {
                            binding.viewPager.currentItem = p0!!.position
                        }

                        override fun onTabUnselected(p0: TabLayout.Tab?) {

                        }
                    })



                }
                catch (e: Exception){
                    Log.d("Exception",e.message.toString())
                }
            }
            }
        }



}