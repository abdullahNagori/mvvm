package com.uhfsolutions.abl.fragment.customerDetail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.databinding.CustomerInfoFragmentBinding
import com.uhfsolutions.abl.model.addLead.DynamicLeadsItem
import com.uhfsolutions.abl.utils.CustomEditText
import com.uhfsolutions.abl.utils.DrawableClickListener
import com.deepakkumardk.kontactpickerlib.KontactPicker
import com.deepakkumardk.kontactpickerlib.model.KontactPickerItem
import com.deepakkumardk.kontactpickerlib.model.SelectionMode

class CustomerInfoFragment : BaseDockFragment() {

    lateinit var number: CustomEditText

    companion object {
        fun newInstance() = CustomerInfoFragment()
    }

    lateinit var dynamicLeadsItem: DynamicLeadsItem
    lateinit var binding: CustomerInfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        initView()

        setData(dynamicLeadsItem)

        binding.call.setOnClickListener {
            coldCallDialog(dynamicLeadsItem.type.toString(),dynamicLeadsItem.mobile_phone_number,dynamicLeadsItem)
        }

        binding.checkin.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(Constants.LOCAL_LEAD_DATA, dynamicLeadsItem)
            bundle.putString(Constants.VISIT_TYPE, Constants.VISIT)
            navigateToFragment(R.id.checkInFormFragment, bundle)
        }

        return binding.root
    }







    private fun initView() {
        binding = CustomerInfoFragmentBinding.inflate(layoutInflater)
    }

    private fun setData(data: DynamicLeadsItem) {
        binding.accountTitle.text = data.first_name
        binding.age.text = data.age
        binding.name.text = data.name
        binding.desc.text = data.desc
        binding.address.text = data.address
        binding.product.text = data.product_name
        binding.jobTitle.text = data.job_title
        binding.gender.text = data.gender
        binding.leadStatus.text = data.lead_status_name
    }

    fun coldCallDialog(customerType: String, contact: String?,customers: DynamicLeadsItem?) {

        val factory = LayoutInflater.from(requireContext())
        val dialogView: View = factory.inflate(R.layout.dialog_call, null)
        val dialog = AlertDialog.Builder(requireContext()).setCancelable(true).create()
        dialog.setView(dialogView)

        number = dialogView.findViewById<CustomEditText>(R.id.call)
        contact?.let {
            number.setText(contact)
        }

        number.setDrawableClickListener(object : DrawableClickListener {
            override fun onClick(target: DrawableClickListener.DrawablePosition?) {
                when (target) {
                    DrawableClickListener.DrawablePosition.RIGHT -> {
                        val item = KontactPickerItem().apply {
                            debugMode = true
                            selectionMode = SelectionMode.Single
                            textBgColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                        }
                        KontactPicker().startPickerForResult(activity, item, 3000)
                    }
                    else -> {
                    }
                }
            }
        })
        val btnCall = dialogView.findViewById<ImageButton>(R.id.btn_call)
        dialog.show()


        btnCall.setOnClickListener {

            if (number.text?.length?.compareTo(11)!! < 0){
                number.error= "invalid number!"
            }else {
                dialog.dismiss()

                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:" + number.text)
                    val bundle = Bundle()
                    customers?.let {
                        bundle.putParcelable(Constants.LOCAL_LEAD_DATA, customers)
                    }
                    bundle.putString(Constants.VISIT_TYPE, Constants.CALL)
                    bundle.putString(Constants.CUSTOMER_TYPE, customerType)
                    bundle.putString("number", number.text.toString())
                    navigateToFragment(R.id.checkInFormFragment, bundle)
                    startActivity(intent)

            }
        }
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 3000) {
            val list = KontactPicker.getSelectedKontacts(data) //ArrayList<MyContacts>
            if (list!!.isNotEmpty()){
                list?.get(0)?.contactNumber?.let {
//                    val intent = Intent(Intent.ACTION_CALL)
//                    intent.data = Uri.parse(it)
                    number.setText(it)
                }
            }


            //Log.i("xxNumber", list[0].)
        }
    }
}