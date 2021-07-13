package com.example.abl.fragment

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.opengl.Visibility
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
import org.koin.android.ext.android.bind
import java.text.SimpleDateFormat
import java.util.*

class CheckInFormFragment : BaseDockFragment(), DatePickerDialog.OnDateSetListener {
    lateinit var binding: CheckInFormFragmentBinding

    private lateinit var mCalender: Calendar
    lateinit var customer: DynamicLeadsItem
    //lateinit var dynamicLeadsItem: DynamicLeadsItem

    var visitStatusList: ArrayList<CompanyVisitStatu> = ArrayList<CompanyVisitStatu>()
    var selectedVisitStatus: CompanyVisitStatu? = null

    var visitType: String = "visit"

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
        getLov()
        getLocation()




        Log.i("xxCall", visitType)
        arguments?.getParcelable<DynamicLeadsItem>(Constants.LEAD_DATA).let {
            it?.let { it1 -> setData(it1) }
        }

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCalender = Calendar.getInstance()

    }

    private fun setData (data: DynamicLeadsItem) {
        customer = data
        binding.customerName.setText(data.first_name)
        binding.contactNo.setText(data.mobile_phone_number)
        visitType = data.type
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
                    myDockActivity?.showErrorMessage("Please fill the form")
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

        visitType = if (arguments?.get(Constants.TYPE) == null){
            "visit"
        } else{
            arguments?.get(Constants.TYPE).toString()
        }

        if (!validationhelper.validateString(binding.customerName)) return
        if (!validationhelper.validateString(binding.contactNo)) return

        if (selectedVisitStatus == null)
            return Toast.makeText(requireContext(),getString(R.string.please_select_status),Toast.LENGTH_SHORT).show()

        Log.i("xxhello",visitType)
        var dict = CheckinModel(binding.accountNo.text.toString(),
            (selectedVisitStatus?.record_id)!!,
            visitType,
            binding.date.text.toString(),
            binding.dateOfConversion.text.toString(),
            customer.customer_id,
            customer.customer_id,
            "",
            "",
            "",
            binding.remarks.text.toString(),
            latitude.toString(),
            longitude.toString())

            addCheckin(dict)
    }

    private fun setCheckInViewWithStatus(status: String) {
        binding.llDate.visibility = GONE
        binding.llDateOfConversion.visibility = GONE

        when (status.lowercase()) {
            "followup" -> {
                binding.llDate.visibility = VISIBLE
            }
            "qualified" -> {
                binding.llDateOfConversion.visibility = VISIBLE
            }
        }
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
                    val lovResponse = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, LovResponse::class.java)
                    visitStatusList = lovResponse?.company_visit_status as ArrayList<CompanyVisitStatu>
                    val index = visitStatusList.indexOfFirst { it.record_id == customer.lead_status }
                    if (index >= 0) {
                        selectedVisitStatus = visitStatusList[index]
                        selectedVisitStatus?.name?.let { setCheckInViewWithStatus(it) }
                    }

                    onClickItemSelected(visitStatusList, index)
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

    private fun onClickItemSelected(lovList: List<CompanyVisitStatu>, selectedPosition: Int) {
        if (lovList.isNotEmpty()) {
                if (lovList.size > 0) {
                    val adapter = CustomVisitAdapter(requireContext(), lovList)
                    binding.status.adapter = adapter
                    binding.status.setSelection(selectedPosition)
                    binding.status.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }

                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                selectedVisitStatus = lovList[position]
                                selectedVisitStatus?.name?.let { setCheckInViewWithStatus(it) }
                            }
                        }
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
}