package com.example.abl.fragment

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.BaseFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.AddFragmentBinding
import com.example.abl.model.*
import com.example.abl.utils.GsonFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class AddLeadFragment : BaseDockFragment() {

    lateinit var binding: AddFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this

        additionalViewVisibility()

        binding.visitLead.setOnClickListener {
            auth()
        }

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

    private fun initView() {
        binding = AddFragmentBinding.inflate(layoutInflater)
    }

    private fun auth() {

        when {
            isEmpty(binding.customerName.text.toString()) -> { showBanner(getString(R.string.error_empty_customer), Constants.ERROR) }
            isEmpty(binding.contactNum.text.toString()) -> { showBanner(getString(R.string.error_empty_contact), Constants.ERROR) }
            isEmpty(binding.companyName.text.toString()) -> { showBanner(getString(R.string.error_empty_company), Constants.ERROR) }
            isEmpty(binding.address.text.toString()) -> { showBanner(getString(R.string.error_empty_address), Constants.ERROR) }
            else ->
                saveData()
//                addLead(CustomerDetail(binding.address.text.toString(),binding.age.text.toString(),binding.cnic.text.toString(),
//                binding.companyName.text.toString(),binding.contactNum.text.toString(),binding.customerName.text.toString(),binding.esIncome.text.toString(),
//                binding.gender.toString(),binding.occupation.toString(),binding.sourceOfIncome.toString()),Checkin("test",
//                "1","2","3","4","5","6","7","8",
//                "9","10"),"Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhbGxpZWQtYmFuayIsInN1YiI6IjEiLCJpYXQiOjE2MjU2MDY5NDIsImV4cCI6MTYyNTYxMDU0MiwiZGF0YSI6eyJzdWNjZXNzIjp0cnVlLCJjb2RlIjowLCJtZXNzYWdlIjoiTG9naW4gU3VjY2Vzc2Z1bGx5Iiwic2lkIjoiMDM1OWMwOWQ2ZDI1YTJhODc0MWQ5OTlkODkzMGNlMzAiLCIyX2ZhIjoieWVzIiwidXNlcl9pZCI6IjEifX0.3IAc9e6pS5U_yFb6yteK2YC65V-sj4BKgZl_wfTG5J8")
        }

    }

    private fun additionalViewVisibility() {
        binding.AdView.setOnClickListener {
            if (binding.llAdditionalInformation.visibility == View.VISIBLE){
                binding.llAdditionalInformation.visibility = View.GONE
                binding.plus.text = "+"
            }else {
                binding.llAdditionalInformation.visibility = View.VISIBLE
                binding.plus.text = "-"
            }
        }

    }

    private fun addLead(customerDetail: CustomerDetail,checkin: Checkin, token: String){
        myDockActivity?.getUserViewModel()?.addLead(AddLeadModelItem(customerDetail, checkin),token)
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when(tag) {
            Constants.ADD_LEAD ->{
                try
                {
//                    Log.d("liveDataValue", liveData.value.toString())
//                    val gson = Gson()
//                    val listType: Type = object : TypeToken<List<AddLeadModelItem?>?>() {}.type
//                    val posts: List<AddLeadModelItem> = gson.fromJson<List<AddLeadModelItem>>(liveData.value, listType)
                    val verifyPassResponseEnt = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, GenericMsgResponse::class.java)
                    Log.i("AddLead", verifyPassResponseEnt.toString())
                   // saveData()

                }
                catch (e: Exception){
                    Log.d("Exception",e.message.toString())
                }
            }

        }
    }

    private fun saveData(){
//        val bundle = Bundle()
//        bundle.putParcelable(Constant.LEAD_DATA)
        navigateToFragment(R.id.action_nav_visit_to_checkInFormFragment)
    }
}