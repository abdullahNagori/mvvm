package com.example.abl.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.abl.R
import com.example.abl.activity.ChangePasswordActivity
import com.example.abl.activity.DockActivity
import com.example.abl.activity.LoginActivity
import com.example.abl.activity.WelcomeActivity
import com.example.abl.common.LoadingListener
import com.example.abl.databinding.DialogPasswordInstructionBinding
import com.example.abl.model.DynamicLeadsItem
import com.example.abl.network.ApiListener
import com.example.abl.utils.DateTimeFormatter
import com.example.abl.utils.SharedPrefManager
import com.example.abl.utils.ValidationHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.android.support.DaggerFragment

import javax.inject.Inject

/**
 * @author Abdullah Nagori
 */


abstract class BaseDockFragment : DaggerFragment(), ApiListener, BaseView {
    protected var myDockActivity: DockActivity? = null
    private var isLoading = false

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    @Inject
    lateinit var validationhelper: ValidationHelper

    @Inject
    lateinit var dateTimeFormatter: DateTimeFormatter

    private lateinit var apiListener: ApiListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myDockActivity = getDockActivity()
        apiListener = this
    }

    protected fun getDockActivity(): DockActivity? {
        return myDockActivity
    }

    protected fun loadingStarted() {
        if (parentFragment != null) (parentFragment as LoadingListener?)?.onLoadingStarted() else getDockActivity()!!
        isLoading = true
    }

    protected fun loadingFinished() {
        if (parentFragment != null) (parentFragment as LoadingListener?)?.onLoadingFinished() else if (getDockActivity() != null) getDockActivity()!!
        isLoading = false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myDockActivity = context as DockActivity
    }

    override fun onStarted() {
       // myDockActivity?.onLoadingStarted()
        //myDockActivity?.showProgressIndicator()
    }

    override fun callDialog(type: String, contact: String?, dynamicLeadsItem: DynamicLeadsItem?) {
        if (activity != null) (activity as DockActivity).showDialog(type,contact,dynamicLeadsItem)
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        myDockActivity?.onSuccessResponse(liveData, tag)
    }

    override fun onFailure(message: String, tag: String) {
        myDockActivity?.onFailureResponse(message, tag)
    }

    override fun onFailureWithResponseCode(code: Int, message: String, tag: String) {
        myDockActivity?.hideProgressIndicator()
        if (code == 551) {
            sharedPrefManager.clear()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        } else if (code == 552) {
            startActivity(Intent(requireContext(), ChangePasswordActivity::class.java))
        }
    }

    override fun showBanner(text: String, type: String) {
        if (activity != null) (activity as DockActivity)
    }

    override fun showPasswordchangingInstructions(text: String?) {
        val alertDialog = BottomSheetDialog(requireContext())
        val viewBinding = DialogPasswordInstructionBinding.inflate(layoutInflater)
        alertDialog.setContentView(viewBinding.root)
        alertDialog.setCancelable(false)

        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        alertDialog.show()
        if (!text.isNullOrEmpty()) {
            viewBinding.txtPasswordInstructions.gravity = Gravity.LEFT
            viewBinding.txtPasswordInstructions.text = text
        }

        viewBinding.btnAvow.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    override fun <T> initiateListArrayAdapter(list: List<T>): ArrayAdapter<T> {
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, list)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        return adapter
    }
}