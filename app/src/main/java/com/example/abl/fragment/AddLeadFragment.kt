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
import com.example.abl.activity.LoginActivity
import com.example.abl.activity.MainActivity
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
        Log.i("navigation","navigate")
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


            if (!validationhelper.validateString(binding.customerName)) return
            if (!validationhelper.validateString(binding.contactNum)) return
            if (!validationhelper.validateString(binding.companyName)) return
            if (!validationhelper.validateString(binding.address)) return

                addLead(CustomerDetail(binding.address.text.toString(),binding.age.text.toString(),binding.cnic.text.toString(),
                binding.companyName.text.toString(),binding.contactNum.text.toString(),binding.customerName.text.toString(),binding.esIncome.text.toString(),
                binding.gender.toString(),binding.occupation.toString(),binding.sourceOfIncome.toString()),Checkin("test",
                "1000","Satisfied","20-02-2021","22-03-2021","5","Apni Car","7","visit",
                "43.24","50.57"))
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

    private fun addLead(customerDetail: CustomerDetail,checkin: Checkin){
        myDockActivity?.getUserViewModel()?.addLead(AddLeadModelItem(customerDetail, checkin))
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
                    MainActivity.navController.navigate(R.id.action_nav_visit_to_checkInFormFragment)


                }
                catch (e: Exception){
                    Log.d("Exception",e.message.toString())
                }
            }

        }
    }




}