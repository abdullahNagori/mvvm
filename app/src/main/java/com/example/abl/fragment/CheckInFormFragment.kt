package com.example.abl.fragment

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.adapter.CustomArrayAdapter
import com.example.abl.adapter.CustomVisitAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.AddFragmentBinding
import com.example.abl.databinding.CheckInFormFragmentBinding
import com.example.abl.model.*
import com.example.abl.utils.GsonFactory
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.item_checkbox.view.*
import java.text.SimpleDateFormat
import java.util.*

class CheckInFormFragment : BaseDockFragment(), DatePickerDialog.OnDateSetListener {


    lateinit var binding: CheckInFormFragmentBinding
    private lateinit var mCalender: Calendar
    lateinit var customer: AddLeadResponseModel

    // private lateinit var visitStatus: CompanyVisitStatu
    private var productDetailsFragment: ProductDialogFragment? = null
    lateinit var visitLovList: ArrayList<CompanyVisitStatu>
    var visitStatusList: ArrayList<CompanyVisitStatu> = ArrayList<CompanyVisitStatu>()
    private lateinit var selectedVisitList: ArrayList<CompanyVisitStatu>
    lateinit var dynamicLeadsItem: DynamicLeadsItem
    private var visitStatus: String? = null
    private var visitName: String? = null
    private var isStatusSelected = false
    private var isProductSelected = false
    lateinit var leadID: String
    lateinit var customerID: String
    lateinit var productID: String
    lateinit var productName: String


    var latitude = 0.0
    var longitude = 0.0


    companion object {
        const val CUS_ID = "customer_id"
        fun newInstance(content: String): CheckInFormFragment {
            val fragment = CheckInFormFragment()
            val args = Bundle()
            args.putString(CUS_ID, content)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this
        //customerID = arguments?.getString(CUS_ID).toString()
        getLov()
        getLocation()
        GsonFactory.getConfiguredGson()?.fromJson(arguments?.getString("customer"), AddLeadResponseModel::class.java).let {
            it?.let {
                binding.customerName.setText(it.first_name)
                binding.contactNo.setText(it.mobile_phone_number)
                //visitStatus = it.status
                customerID = it.customer_id
                leadID = it.lead_id
                productID = it.record_id
                productName = it.product_name
            }
        }

        arguments?.getParcelable<DynamicLeadsItem>(Constants.LEAD_DATA).let {
            it?.let { it1 -> setData(it1) }
        }

//        binding.product.setOnClickListener {
//            onClickItemSelected(productLovList)
//        }

        binding.dateOfConversion.setOnClickListener {
            DatePickerDialog(
                myDockActivity!!, R.style.DialogTheme, this, mCalender
                    .get(Calendar.YEAR), mCalender.get(Calendar.MONTH),
                mCalender.get(Calendar.DAY_OF_MONTH)
            ).show()
            binding.dateOfConversion.setTextColor(
                ContextCompat.getColor(
                    myDockActivity!!,
                    R.color.colorAccent
                )
            )
        }

        binding.submit.setOnClickListener {
            auth()
        }

        binding.time.setOnClickListener {
            showTimeDialog(requireContext(), binding.time)
        }

        statusWiseViews()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCalender = Calendar.getInstance()

    }

    private fun setData (data: DynamicLeadsItem){
        binding.customerName.setText(data.first_name.toString())
        binding.contactNo.setText(data.mobile_phone_number)
        visitStatus = data.status!!
        customerID = data.customer_id!!
    }

    override fun closeDrawer() {

    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun setTitle(text: String) {
        TODO("Not yet implemented")
    }

    private fun initView() {

        binding = CheckInFormFragmentBinding.inflate(layoutInflater)
        binding.date.setOnClickListener {
            DatePickerDialog(
                myDockActivity!!, R.style.DialogTheme, this, mCalender
                    .get(Calendar.YEAR), mCalender.get(Calendar.MONTH),
                mCalender.get(Calendar.DAY_OF_MONTH)
            ).show()
            binding.date.setTextColor(
                ContextCompat.getColor(
                    myDockActivity!!,
                    R.color.colorAccent
                )
            )

        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showBanner("Please Fill the From", Constants.ERROR)
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mCalender.set(Calendar.YEAR, year)
        mCalender.set(Calendar.MONTH, month)
        mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateLabel()
    }

    private fun updateLabel() {
        val sdf = SimpleDateFormat(Constants.DATE_FORMAT_1, Locale.ENGLISH)
        binding.date.text = sdf.format(mCalender.time)
        binding.date.setTextColor(ContextCompat.getColor(myDockActivity!!, R.color.black))
        binding.dateOfConversion.text = sdf.format(mCalender.time)
        binding.dateOfConversion.setTextColor(
            ContextCompat.getColor(
                myDockActivity!!,
                R.color.black
            )
        )
    }

    private fun addCheckin(checkinModel: CheckinModel) {
        myDockActivity?.getUserViewModel()?.addLeadCheckin(checkinModel)
    }

    private fun auth() {
        if (!validationhelper.validateString(binding.customerName)) return
        if (!validationhelper.validateString(binding.accountNo)) return
        if (!validationhelper.validateString(binding.contactNo)) return
        if (!validationhelper.validateString(binding.amount)) return
        if (!isStatusSelected)
            return Toast.makeText(requireContext(),getString(R.string.please_select_status),Toast.LENGTH_SHORT).show()
        if (!isProductSelected)
            return Toast.makeText(requireContext(),getString(R.string.please_select_product),Toast.LENGTH_SHORT).show()

            addCheckin(
                CheckinModel(
                    binding.accountNo.text.toString(),
                    binding.amount.text.toString(),
                    binding.remarks.text.toString(),
                    customerID.toString(),
                    binding.dateOfConversion.text.toString(),
                    binding.date.toString(),
                    leadID,
                    productID,
                    productName,
                    "visit",
                    visitName.toString(),
                    latitude.toString(),
                    longitude.toString()
                )
            )



    }

    fun showTimeDialog(context: Context, displayView: TextView) {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mcurrentTime[Calendar.MINUTE]
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(
            context,
            { timePicker, selectedHour, selectedMinute ->

                var am_pm = ""

                val datetime = Calendar.getInstance()
                datetime.set(Calendar.HOUR_OF_DAY, selectedHour)
                datetime[Calendar.MINUTE] = minute

                if (datetime[Calendar.AM_PM] === Calendar.AM) am_pm =
                    "AM" else if (datetime[Calendar.AM_PM] === Calendar.PM) am_pm = "PM"
                displayView.text = "$selectedHour : $selectedMinute $am_pm"
            },
            hour,
            minute,
            true
        )

        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }


    private fun getLov() {
        myDockActivity?.getUserViewModel()?.getLovs()
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.GET_LOVS -> {
                try {
                    Log.i("AddLead", liveData.value.toString())
                    val lovResponse = GsonFactory.getConfiguredGson()
                        ?.fromJson(liveData.value, LovResponse::class.java)
                  ///  productLovList = lovResponse?.company_products as ArrayList<CompanyProduct>
                    visitStatusList = lovResponse?.company_visit_status as ArrayList<CompanyVisitStatu>
                    onClickItemSelected(visitStatusList)
                    //binding.status.adapter = initiateListArrayAdapter(visitStatusList)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            Constants.ADD_LEAD_CHECKIN -> {
                try {
                    Log.i("AddLead", liveData.value.toString())
                    val checkinResponse = GsonFactory.getConfiguredGson()
                        ?.fromJson(liveData.value, GenericMsgResponse::class.java)
                    Toast.makeText(requireContext(), checkinResponse?.message, Toast.LENGTH_SHORT)
                        .show()
                    Log.i("xxID", checkinResponse?.message.toString())
                    MainActivity.navController.navigate(R.id.action_checkInFormFragment_to_nav_home)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
        }
    }


    private fun statusWiseViews() {
        binding.status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ) {
                var vs = (binding.status.selectedItem)
                when {
                    vs.toString().equals("Select Status") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = GONE //follow-up date
                    }
                    vs.toString().equals("QUALIFIED") -> {
                        binding.llDateOfConversion.visibility = VISIBLE
                        binding.llDate.visibility = GONE
                        isStatusSelected = true
                    }
                    vs.toString().equals("INPROCESS") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = GONE
                        isStatusSelected = true
                    }
                    vs.toString().equals("CLOSED") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = GONE
                        isStatusSelected = true
                    }
                    vs.toString().equals("FOLLOWUP") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = VISIBLE
                        isStatusSelected = true

                    }
                    vs.toString().equals("OPEN") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = VISIBLE
                        isStatusSelected = true
                    }
                    vs.toString().equals("INPROCESS") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = GONE
                        isStatusSelected = true
                    }
                    vs.toString().equals("INTERESTED") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = GONE
                    }
                    vs.toString().equals("NOT AVAILABLE") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = GONE
                    }
                }

                visitStatus = vs.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun onClickItemSelected(lovList: List<CompanyVisitStatu>) {
        if (lovList.isNotEmpty()) {
            if ((this::visitLovList.isInitialized)) {
                if (visitLovList.size > 0) {
                    val adapter = CustomVisitAdapter(requireContext(), lovList)
                    binding.status.adapter = adapter
                    binding.status.onItemSelectedListener =
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
                                visitName = visitLovList[position].name
                               // productID = visitLovList[position].product_code
                            }
                        }
                }
            } else {
                Log.i("Error4", "No data found $lovList")

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

//    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        when (parent?.id) {
//            R.id.status -> {
//                visitStatus = parent.getItemAtPosition(position) as String
//            }
//            R.id.product_spinner -> {
//                productName = parent.getItemAtPosition(position) as String
//                productID = parent.getItemAtPosition(position) as String
//                productID = selectedList[position].product_code
//
//            }
//        }
//    }
//
//    override fun onNothingSelected(parent: AdapterView<*>?) {
//
//    }

}