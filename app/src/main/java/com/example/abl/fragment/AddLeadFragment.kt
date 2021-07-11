package com.example.abl.fragment

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.adapter.CustomArrayAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.AddFragmentBinding
import com.example.abl.model.*
import com.example.abl.utils.GsonFactory
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.add_fragment.*
import java.util.*


class AddLeadFragment : BaseDockFragment(), AdapterView.OnItemSelectedListener {

    private var gender: String? = null
    private var sourceOfIncome: String? = null
    private var occupation: String? = null
    lateinit var productLovList: ArrayList<CompanyProduct>
    var selectedList: ArrayList<String>? = null
    var productName: String? = null
    var productID: String? = null
    var latitude = 0.0
    var longitude = 0.0
    lateinit var binding: AddFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this
        getLov()
        additionalViewVisibility()
        binding.occupation.onItemSelectedListener = this
        binding.sourceOfIncome.onItemSelectedListener = this
        binding.gender.onItemSelectedListener = this
        getLocation()
        binding.visitLead.setOnClickListener {
            auth()
        }

//        binding.productAddLead.setOnClickListener{
//            onClickItemSelected(productLovList)
//        }


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
//        val adapter = CustomArrayAdapter(requireContext(), R.layout.item_spinner, list)
//        adapter.setDropDownViewResource(R.layout.item_spinner)
//        return adapter
    }

    private fun initView() {
        binding = AddFragmentBinding.inflate(layoutInflater)
    }

    private fun auth() {

        if (!validationhelper.validateString(binding.customerName)) return
        if (!validationhelper.validateString(binding.contactNum)) return
        if (!validationhelper.validateString(binding.companyName)) return
        if (!validationhelper.validateString(binding.address)) return
        if (!validationhelper.validateString(binding.amount)) return

        addLead(
            CustomerDetail(
                binding.customerName.text.toString(),
                binding.contactNum.text.toString(),
                binding.companyName.text.toString(),
                binding.address.text.toString(),
                binding.cnic.text.toString(),
                occupation.toString(),
                sourceOfIncome.toString(),
                binding.esIncome.text.toString(),
                binding.age.text.toString(),
                gender.toString(),
                latitude.toString(),
                longitude.toString(),
                productID.toString(),
                productName.toString(),
                binding.amount.text.toString(),
                "followup",
                "10-07-2021",
                "Interested"
            )
        )


    }


    private fun additionalViewVisibility() {
        binding.AdView.setOnClickListener {
            if (binding.llAdditionalInformation.visibility == View.VISIBLE) {
                binding.llAdditionalInformation.visibility = View.GONE
                binding.plus.text = "+"
            } else {
                binding.llAdditionalInformation.visibility = View.VISIBLE
                binding.plus.text = "-"
            }
        }

    }


    private fun addLead(customerDetail: CustomerDetail) {
        myDockActivity?.getUserViewModel()?.addLead(customerDetail)
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.ADD_LEAD -> {
                try {
                    //Log.i("AddLead", liveData.value.toString())
                    val addLeadResponse = GsonFactory.getConfiguredGson()
                        ?.fromJson(liveData.value, AddLeadResponse::class.java)
                    if (addLeadResponse?.data != null) {
                        val bundle = Bundle()
                        //bundle.putString("customer_id", cusID)
                        bundle.putString(
                            "customer", GsonFactory.getConfiguredGson()?.toJson(
                                addLeadResponse.data
                            )!!
                        )
                        //Toast.makeText(requireContext(),verifyPassResponseEnt?.message,Toast.LENGTH_SHORT).show()
                        //Log.i("xxID", verifyPassResponseEnt?.message.toString())
                        navigateToFragment(R.id.action_nav_visit_to_checkInFormFragment, bundle)
                    }

                    //Log.i("AddLead", verifyPassResponseEnt?.message.toString())
                    //verifyPassResponseEnt.data.
                    //CheckInFormFragment.newInstance(verifyPassResponseEnt?.data?.customer_id.toString())

                    //Log.i("xxID", verifyPassResponseEnt?.data?.customer_id.toString())
//                    MainActivity.navController.navigate(R.id.action_nav_visit_to_checkInFormFragment)
                    //var cusID = verifyPassResponseEnt?.data?.customer_id
                    //bundle.putString("customer_id", cusID)
                    //Toast.makeText(requireContext(),verifyPassResponseEnt?.message,Toast.LENGTH_SHORT).show()
                    //Log.i("xxID", verifyPassResponseEnt?.message.toString())
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            Constants.GET_LOVS -> {
                try {
                    Log.i("AddLead", liveData.value.toString())
                    val lovResponse = GsonFactory.getConfiguredGson()
                        ?.fromJson(liveData.value, LovResponse::class.java)
                    productLovList = lovResponse?.company_products as ArrayList<CompanyProduct>
                    onClickItemSelected(productLovList)
                    //binding.status.adapter = initiateListArrayAdapter(visitStatusList)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

        }
    }

    private fun onClickItemSelected(lovList: List<CompanyProduct>) {
        if (lovList.isNotEmpty()) {
            if ((this::productLovList.isInitialized)) {
                if (productLovList.size > 0) {
                    val adapter = CustomArrayAdapter(requireContext(), lovList)
                    binding.productSpinner.adapter = adapter

                    binding.productSpinner.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }

                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
//                            sourceOfIncome = parent?.getItemAtPosition(position) as String
//                            val selectedItemText: String =
//                                productLovList[binding.productSpinner.selectedItemPosition].product_name

                                productName = productLovList[position].product_name
                                productID = productLovList[position].product_code
                            }
                        }
                }
            } else {
                Log.i("Error4", "No data found $lovList")

            }
        }
    }

    private fun getLov() {
        myDockActivity?.getUserViewModel()?.getLovs()
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

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        } else {
            LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation.addOnSuccessListener {
                try {
                    if (it == null) {
                        getLocation()
                    }
                    latitude = it.latitude
                    longitude = it.longitude
                } catch (e: java.lang.Exception) {
                }
            }

        }
    }


}