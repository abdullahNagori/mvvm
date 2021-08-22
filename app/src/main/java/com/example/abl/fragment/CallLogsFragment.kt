package com.example.abl.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.adapter.CallLogsAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.BaseFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.CallLogsFragmentBinding
import com.example.abl.model.DynamicLeadsResponse
import com.example.abl.utils.GsonFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


data class CallLogsList(

    var time: String,
    var mobile: String,
    var name: String,
    var status: String,
    var ampm: String
        )

class CallLogsFragment : BaseDockFragment(), ClickListner {

    companion object {
        fun newInstance() = CallLogsFragment()
    }

    lateinit var binding: CallLogsFragmentBinding
    private var data: DynamicLeadsResponse? = null

    lateinit var adapter: CallLogsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this

        binding.search.setOnClickListener {
            showCustomerDialog()
        }

        binding.filter.setOnClickListener {
            showHideFilter()
        }

    //    dummyData(binding.logs)
        getDynamicData("")
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

    private fun initView(){
        binding = CallLogsFragmentBinding.inflate(layoutInflater)
    }

    private fun getDynamicData(token: String){
        myDockActivity?.getUserViewModel()?.getDynamicLeads(token)
    }

//    private fun dummyData(ltd: RecyclerView){
//
//        dataList.clear()
//        val nameOne = "12:30"
//        val numberOne = "0312XXXXXXX"
//        val accountOne = "Aftab Alam"
//        val leadStatusOne = "Followup"
//        val am = "AM"
//
//        val nameTwo = "01:18"
//        val numberTwo = "0345XXXXXXX"
//        val accountTwo = "Chota Shakeel"
//        val leadStatusTwo = "Closed"
//        val pm = "PM"
//
//        val objectOne = CallLogsList(nameOne, numberOne, accountOne, leadStatusOne, am)
//        val objectTwo = CallLogsList(nameTwo, numberTwo, accountTwo, leadStatusTwo, pm)
//        dataList.add(objectOne)
//        dataList.add(objectTwo)
//
//        if (dataList.isEmpty())
//        {
//            Log.i("list", "null")
//        }
//        else
//        {
//            adapter = CallLogsAdapter(requireContext(), this)
//            adapter.setList(dataList)
//            adapter.notifyDataSetChanged()
//            ltd.adapter = adapter
//        }
//
//    }

    private fun showCustomerDialog(){
        val callLogSearchDialogFragment = CallLogSearchFragment()
        callLogSearchDialogFragment.show(childFragmentManager, "visits")
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.GET_DYNAMIC_LEADS -> {
                try {
                    Log.d("liveDataValue", liveData.value.toString())
//                    val gson = Gson()
//                    val listType: Type = object : TypeToken<List<DynamicLeadsResponse?>?>() {}.type
//                    val posts: List<DynamicLeadsResponse> = gson.fromJson<List<DynamicLeadsResponse>>(liveData.value, listType)
                    val routeResponseEnt = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, DynamicLeadsResponse::class.java)
                    Log.i("xxData",routeResponseEnt?.data.toString())
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
        }
    }
    override fun <T> onClick(data: T, createNested: Boolean) {
        val logDetailsFragment = CallLogDetailFragment()
        logDetailsFragment.show(childFragmentManager, "visits")
    }


    private fun showHideFilter(){
        if(binding.filterLayout.root.visibility == View.VISIBLE){
            binding.filterLayout.root.visibility = View.GONE
        }else {
            binding.filterLayout.root.visibility = View.VISIBLE
        }
    }
}