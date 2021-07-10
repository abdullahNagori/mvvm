package com.example.abl.fragment

import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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
import kotlinx.android.synthetic.main.add_fragment.*
import java.lang.reflect.Type

class AddLeadFragment : BaseDockFragment(), AdapterView.OnItemSelectedListener {

    private var gender : String? = null
    private var sourceOfIncome : String? = null
    private var occupation : String? = null
    lateinit var binding: AddFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this

        additionalViewVisibility()
        binding.occupation.onItemSelectedListener = this
        binding.sourceOfIncome.onItemSelectedListener = this
        binding.gender.onItemSelectedListener = this
        binding.visitLead.setOnClickListener {
            auth()
        }

        return binding.root
    }

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        if (args != null) {
            MainActivity.navController.navigate(id, args)
            return
        }
        MainActivity.navController.navigate(id)
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
                addLead(CustomerDetail(binding.customerName.text.toString(),binding.contactNum.text.toString(),binding.companyName.text.toString(),
                    binding.address.text.toString(),binding.cnic.text.toString(),occupation.toString(),sourceOfIncome.toString(),binding.esIncome.text.toString(),
                    binding.age.text.toString(),gender.toString(),"23.45","36.48","7","test","2000","visit",
                    "10-07-2021","Interested"))
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

    private fun addLead(customerDetail: CustomerDetail){
        myDockActivity?.getUserViewModel()?.addLead(customerDetail)
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when(tag) {
            Constants.ADD_LEAD ->{
                try
                {
                    Log.i("AddLead", liveData.value.toString())
                    val verifyPassResponseEnt = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, AddLeadResponse::class.java)
                    Log.i("AddLead", verifyPassResponseEnt?.message.toString())
                    //verifyPassResponseEnt.data.
                    CheckInFormFragment.newInstance(verifyPassResponseEnt?.data?.customer_id.toString())

                    Log.i("xxID", verifyPassResponseEnt?.data?.customer_id.toString())
//                    MainActivity.navController.navigate(R.id.action_nav_visit_to_checkInFormFragment)
                    var cusID = verifyPassResponseEnt?.data?.customer_id
                    val bundle = Bundle()
                    bundle.putString("customer_id", cusID)
                    Toast.makeText(requireContext(),verifyPassResponseEnt?.message,Toast.LENGTH_SHORT).show()
                    Log.i("xxID", verifyPassResponseEnt?.message.toString())
                    navigateToFragment(R.id.action_nav_visit_to_checkInFormFragment, bundle)
                }
                catch (e: Exception){
                    Log.d("Exception",e.message.toString())
                }
            }

        }
    }

    override fun onFailure(message: String, tag: String) {
        super.onFailure(message, tag)
        myDockActivity?.showErrorMessage(message)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.occupation -> {
                occupation = parent.getItemAtPosition(position) as String
            }
            R.id.gender -> {
                gender = parent.getItemAtPosition(position) as String
            }
            R.id.source_of_income -> {
                sourceOfIncome = parent.getItemAtPosition(position) as String

            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}