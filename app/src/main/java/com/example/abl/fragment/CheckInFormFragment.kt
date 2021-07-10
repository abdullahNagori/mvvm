package com.example.abl.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.AddFragmentBinding
import com.example.abl.databinding.CheckInFormFragmentBinding
import com.example.abl.model.*
import com.example.abl.utils.GsonFactory
import java.text.SimpleDateFormat
import java.util.*

class CheckInFormFragment : BaseDockFragment(), DatePickerDialog.OnDateSetListener, ClickListner {


    lateinit var binding: CheckInFormFragmentBinding
    private lateinit var mCalender: Calendar
    private lateinit var visitStatus: CompanyVisitStatu
    private var customerID: AddLeadResponse? = null
    private  var productDetailsFragment : ProductDialogFragment ?=null
    var productLovList : ArrayList<CompanyProduct> = ArrayList<CompanyProduct>()
    var visitStatusList : ArrayList<CompanyVisitStatu> = ArrayList<CompanyVisitStatu>()
    private lateinit var selectedList : ArrayList<CompanyProduct>

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
        customerID = GsonFactory.getConfiguredGson()?.fromJson(arguments?.getString(CUS_ID), AddLeadResponse::class.java)

        Log.i("xxCustomer", customerID.toString())
        Log.i("xxCustomer1", arguments?.getString(CUS_ID)!!)

       // var id = arguments?.getString(CUS_ID)


        myDockActivity?.getUserViewModel()?.apiListener = this
        getLov()
        binding.product.setOnClickListener {
            onClickItemSelected(productLovList)
        }
        statusWiseViews()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCalender = Calendar.getInstance()

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
        binding.dateOfConversion.setTextColor(ContextCompat.getColor(myDockActivity!!, R.color.black))
    }

    private fun auth(){
        if (!validationhelper.validateString(binding.customerName)) return
        if (!validationhelper.validateString(binding.accountNo)) return
        if (!validationhelper.validateString(binding.contactNo)) return
        if (!validationhelper.validateString(binding.amount)) return
        if (!validationhelper.validateSpinner(binding.status,"Please Select Status ")) return
        if (!validationhelper.validateSpinner(binding.s,"Please Select Product ")) return

    Toast.makeText(requireContext(),"Submitted Successfully", Toast.LENGTH_SHORT).show()
    MainActivity.navController.navigate(R.id.action_checkInFormFragment_to_nav_home)

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


    private fun getLov(){
        myDockActivity?.getUserViewModel()?.getLovs()
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when(tag){
            Constants.GET_LOVS ->{
                try
                {
                    Log.i("AddLead", liveData.value.toString())
                    val lovResponse = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, LovResponse::class.java)
                    productLovList = lovResponse?.company_products as ArrayList<CompanyProduct>
                    visitStatusList = lovResponse.company_visit_status as ArrayList<CompanyVisitStatu>
                   //binding.status.adapter = initiateListArrayAdapter(visitStatusList)

                }
                catch (e: Exception){
                    Log.d("Exception",e.message.toString())
                }
            }
        }
    }

    private fun onClickItemSelected(lovList: List<CompanyProduct>) {
        if (productDetailsFragment==null) {
            if (lovList.isNotEmpty()) {
                productDetailsFragment = ProductDialogFragment(this)
                productDetailsFragment!!.listOfProduct = lovList
                if ((this::selectedList.isInitialized)) {
                    if (selectedList.size > 0) {
                        productDetailsFragment!!.alreadySelectedProduct = selectedList
                    }
                } else {
                    productDetailsFragment!!.alreadySelectedProduct = arrayListOf()
                }

                productDetailsFragment!!.isCancelable = false;
                productDetailsFragment!!.show(childFragmentManager, "checkin")
            } else {
                showBanner("No data found, please sync", Constants.ERROR)
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
                var vs  = (binding.status.selectedItem)
                when {
                    vs.toString().equals("Select Status") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = GONE //follow-up date
                    }
                    vs.toString().equals("QUALIFIED") -> {
                        binding.llDateOfConversion.visibility = VISIBLE
                        binding.llDate.visibility = GONE
                    }
                    vs.toString().equals("INPROCESS") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = GONE
                    }
                    vs.toString().equals("CLOSED") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = GONE
                    }
                    vs.toString().equals("FOLLOWUP") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = VISIBLE
                    }
                    vs.toString().equals("NOT INTERESTED") -> {
                        binding.llDateOfConversion.visibility = GONE
                        binding.llDate.visibility = GONE
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
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        if (productDetailsFragment != null) {
            productDetailsFragment!!.dismiss();
            productDetailsFragment = null;
        }
        if (::selectedList.isInitialized) selectedList.clear()
        selectedList = data as ArrayList<CompanyProduct>
        binding.product.text = selectedList.size.toString()  + " Items Selected"
    }

}