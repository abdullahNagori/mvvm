package com.uhfsolutions.abl.fragment.salesManagement

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.uhfsolutions.abl.base.BaseDialogFragment
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.databinding.CallLogSearchFragmentBinding
import kotlinx.android.synthetic.main.call_log_search_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class CallLogSearchFragment : BaseDialogFragment() {

    lateinit var binding: CallLogSearchFragmentBinding
    private lateinit var mCalender: Calendar
    private var toDateTxt: String? = null
    private var fromDateTxt: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment

        initView()

        return binding.root
    }

    private fun initView(){

        binding = CallLogSearchFragmentBinding.inflate(layoutInflater)
        mCalender = Calendar.getInstance()

        binding.closeAction.setOnClickListener {
            dialog!!.dismiss()
        }

        val ToDate =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                mCalender.set(Calendar.YEAR, year)
                mCalender.set(Calendar.MONTH, monthOfYear)
                mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelToDate()
            }
        val FromDate =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                mCalender.set(Calendar.YEAR, year)
                mCalender.set(Calendar.MONTH, monthOfYear)
                mCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelFromDate()
            }

        binding.fromDate.setOnClickListener {
            DatePickerDialog(
                requireContext(), FromDate, mCalender
                    .get(Calendar.YEAR), mCalender.get(Calendar.MONTH),
                mCalender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.toDate.setOnClickListener {
            DatePickerDialog(
                requireContext(), ToDate, mCalender
                    .get(Calendar.YEAR), mCalender.get(Calendar.MONTH),
                mCalender.get(Calendar.DAY_OF_MONTH)
            ).show()

        }
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    private fun updateLabelToDate(){
        val sdf = SimpleDateFormat(Constants.DATE_FORMAT_1, Locale.ENGLISH)
        to_date.text = sdf.format(mCalender.time)
        toDateTxt = sdf.format(mCalender.time)
    }

    private fun updateLabelFromDate(){
        val sdf = SimpleDateFormat(Constants.DATE_FORMAT_1, Locale.ENGLISH)
        from_date.text = sdf.format(mCalender.time)
        fromDateTxt = sdf.format(mCalender.time)
    }
}