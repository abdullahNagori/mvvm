package com.example.abl.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.abl.activity.DockActivity
import com.example.abl.common.LoadingListener
import com.example.abl.network.ApiListener
import com.example.abl.utils.DateTimeFormatter
import com.example.abl.utils.SharedPrefManager
import com.example.abl.utils.ValidationHelper
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

    override fun onResume() {
        super.onResume()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    protected fun loadingStarted() {
        if (parentFragment != null) (parentFragment as LoadingListener?)?.onLoadingStarted() else getDockActivity()!!.onLoadingStarted()
        isLoading = true
    }

    protected fun loadingFinished() {
        if (parentFragment != null) (parentFragment as LoadingListener?)?.onLoadingFinished() else if (getDockActivity() != null) getDockActivity()!!.onLoadingFinished()
        isLoading = false
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        myDockActivity = context as DockActivity
//    }

    override fun onStarted() {
        myDockActivity?.onLoadingStarted()
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        myDockActivity?.onLoadingFinished()
    }

    override fun onFailure(message: String, tag: String) {
        myDockActivity?.onLoadingFinished()
        myDockActivity?.showErrorMessage(message)
    }

    override fun showBanner(text: String, type: String) {
        if (activity != null) (activity as BaseActivity).showBanner(text, type)
    }


}