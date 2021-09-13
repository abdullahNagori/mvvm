package com.example.abl.fragment.customerDetail

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.base.BaseDockFragment
import com.example.abl.databinding.MeetingQrFragmentBinding
import com.example.abl.model.addLead.DynamicLeadsItem
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder


class MeetingQRFragment : BaseDockFragment() {

    companion object {
        fun newInstance() = CalculatorFragment()
    }

    lateinit var dynamicLeadsItem: DynamicLeadsItem
    lateinit var binding: MeetingQrFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initView()

        initQRCode("Allied Bank AMC")

        return binding.root
    }





    private fun initView() {
        binding = MeetingQrFragmentBinding.inflate(layoutInflater)
    }

    private fun initQRCode(value: String) {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix =
                multiFormatWriter.encode(value.toString(), BarcodeFormat.QR_CODE, 600, 600)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)
            binding.qrGenerator.setImageBitmap(bitmap)
            binding.qrGenerator.visibility = View.VISIBLE
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}