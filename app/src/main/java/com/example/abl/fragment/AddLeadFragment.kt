package com.example.abl.fragment

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Parcelable
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
import com.example.abl.activity.DockActivity
import com.example.abl.activity.MainActivity
import com.example.abl.adapter.CustomArrayAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.AddFragmentBinding
import com.example.abl.model.*
import com.example.abl.utils.GsonFactory
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.add_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class AddLeadFragment : BaseDockFragment(), AdapterView.OnItemSelectedListener {
    lateinit var binding: AddFragmentBinding

    var selectedProduct: CompanyProduct? = null
    private var sourceOfIncome: String? = null
    private var gender: String? = null
    private var occupation: String? = null

    lateinit var productLovList: ArrayList<CompanyProduct>

    //var selectedList: ArrayList<String>? = null
    var latitude = 0.0
    var longitude = 0.0

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
            addLead()
        }

        setProductSpinner()
        getLocation()

        return binding.root
    }

    private fun initView() {
        binding = AddFragmentBinding.inflate(layoutInflater)
    }

    private fun addLead() {
        if (!validationhelper.validateString(binding.customerName)) return
        if (!validationhelper.validateString(binding.contactNum)) return
        if (!validationhelper.validateString(binding.companyName)) return
        if (!validationhelper.validateString(binding.address)) return
        if (!validationhelper.validateString(binding.amount)) return

        if (selectedProduct == null) {
            myDockActivity?.showErrorMessage("Please select product")
            return
        }

        val customerDetail = CustomerDetail(
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
            (selectedProduct?.record_id)!!,
            (selectedProduct?.product_name)!!,
            binding.amount.text.toString(),
            "",
            "",
            "")

        myDockActivity?.getUserViewModel()?.addLead(customerDetail)
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

    private fun setProductSpinner() {
        val products = sharedPrefManager.getCompanyProducts()
        if (products != null) {
            productLovList = products as ArrayList<CompanyProduct>
            val adapter = CustomArrayAdapter(requireContext(), productLovList)
            binding.productSpinner.adapter = adapter
            binding.productSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        selectedProduct = productLovList[position];
                    }
                }
        }
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.ADD_LEAD -> {
                try {
                    val leadModel = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, DynamicLeadsItem::class.java)
                    if (leadModel != null) {
                        val bundle = Bundle()
                        bundle.putParcelable(Constants.LEAD_DATA, leadModel as Parcelable)
                        navigateToFragment(R.id.action_nav_visit_to_checkInFormFragment, bundle)
                    }
                } catch (e: Exception) {
                    myDockActivity?.showErrorMessage("Something went wrong")
                }
            }
        }
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

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}