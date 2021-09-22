package com.example.abl.fragment.leadManagement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.adapter.DynamicViewPagerAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.FragmentCrmBinding
import com.example.abl.model.lov.CompanyLeadSource
import com.example.abl.model.addLead.DynamicLeadsResponse
import com.example.abl.utils.GsonFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_crm.*
import kotlinx.android.synthetic.main.portfolio_fragment.*
import java.lang.reflect.Type


class CRMFragment : BaseDockFragment() {
    lateinit var binding: FragmentCrmBinding
    lateinit var adapter: DynamicViewPagerAdapter
    lateinit var leadSourceData: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myDockActivity?.getUserViewModel()?.apiListener = this
        binding = FragmentCrmBinding.inflate(layoutInflater)
        leadSourceData = arguments?.getString(Constants.LEAD_SOURCE_DATA).toString()

        // Set navigation title
        val leadSource = GsonFactory.getConfiguredGson()?.fromJson(leadSourceData, CompanyLeadSource::class.java)
        myDockActivity?.supportActionBar?.title = leadSource?.name ?: getString(R.string.lead_management)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }


    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.GET_DYNAMIC_LEADS -> {
                try {
                    Log.d("liveDataValue", liveData.value.toString())
                    val gson = Gson()
                    val listType: Type = object : TypeToken<List<DynamicLeadsResponse?>?>() {}.type
                    val posts: List<DynamicLeadsResponse> = gson.fromJson<List<DynamicLeadsResponse>>(liveData.value, listType)
                    //setupViewPager(posts)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
        }
    }

    private fun setupViewPager() {
        val leadStatusArray = sharedPrefManager.getLeadStatus()
        binding.viewPager.offscreenPageLimit = 10
        if (leadStatusArray != null) {
            adapter = DynamicViewPagerAdapter(childFragmentManager, leadStatusArray.size, leadStatusArray, leadSourceData)
            binding.viewPager.adapter = adapter
            binding.tabLayout.setupWithViewPager(binding.viewPager)

            leadStatusArray.forEachIndexed { index, element ->
                binding.tabLayout.getTabAt(index)?.text = element.name
            }
        }
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