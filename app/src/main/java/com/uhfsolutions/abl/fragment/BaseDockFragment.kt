package com.uhfsolutions.abl.fragment

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.activity.DockActivity
import com.uhfsolutions.abl.activity.MainActivity
import com.uhfsolutions.abl.common.LoadingListener
import com.uhfsolutions.abl.network.ApiListener
import com.uhfsolutions.abl.room.RoomHelper
import com.uhfsolutions.abl.utils.DateTimeFormatter
import com.uhfsolutions.abl.utils.SharedPrefManager
import com.uhfsolutions.abl.utils.UtilHelper
import com.uhfsolutions.abl.utils.ValidationHelper
import dagger.android.support.DaggerFragment
import java.io.OutputStream
import java.util.*
import javax.inject.Inject

/**
 * @author Abdullah Nagori
 */


abstract class BaseDockFragment : DaggerFragment(), ApiListener {

    protected var myDockActivity: DockActivity? = null

    private var isLoading = false

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    @Inject
    lateinit var roomHelper: RoomHelper

    @Inject
    lateinit var validationhelper: ValidationHelper

    @Inject
    lateinit var dateTimeFormatter: DateTimeFormatter

    @Inject
    lateinit var utilHelper: UtilHelper

    private lateinit var apiListener: ApiListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myDockActivity = getDockActivity()
        apiListener = this
    }

    private fun getDockActivity(): DockActivity? {
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

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        myDockActivity?.onSuccessResponse(liveData, tag)
    }

    override fun onFailure(message: String, tag: String) {
        myDockActivity?.onFailureResponse(message, tag)
    }

    override fun onFailureWithResponseCode(code: Int, message: String, tag: String) {
        myDockActivity?.hideProgressIndicator()
    }

    fun showBanner(text: String, type: String) {
        if (activity != null) (activity as DockActivity)
    }

    fun navigateToFragment(@IdRes id: Int, args: Bundle? = null) {
        if (args != null) {
            MainActivity.navController.navigate(id, args)
            return
        }
        MainActivity.navController.navigate(id)
    }

}