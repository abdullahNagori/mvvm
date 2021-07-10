package com.example.abl.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.abl.R
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.BaseFragment
import com.example.abl.base.BaseView
import com.example.abl.common.LoadingListener
import com.example.abl.network.ApiListener
import com.example.abl.progress.ProgressDialog
import com.example.abl.progress.ProgressIndicator
import com.example.abl.utils.SharedPrefManager
import com.example.abl.viewModel.UserViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * @author Abdullah Nagori
 */


abstract class DockActivity : DaggerAppCompatActivity(), ApiListener, ProgressIndicator {
    abstract fun getDockFrameLayoutId(): Int

    val KEY_FRAG_FIRST = "firstFrag"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var progressBarDialog: ProgressDialog
    private lateinit var userViewModel: UserViewModel
    private lateinit var apiListener: ApiListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        apiListener = this
    }

    private fun initViewModels(){
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
    }

    fun replaceDockableFragment(frag: BaseFragment) {
        val transaction = supportFragmentManager
            .beginTransaction()

        transaction.replace(getDockFrameLayoutId(), frag)
        transaction
            .addToBackStack(
                if (supportFragmentManager.backStackEntryCount == 0) KEY_FRAG_FIRST else null).commit()
    }

    fun hideKeyboard(view: View) {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun replaceDockableFragmentWithoutBackStack(frag: BaseFragment?) {
        val transaction = supportFragmentManager
            .beginTransaction()
        transaction.replace(getDockFrameLayoutId(), frag!!)
        transaction.commit()
    }

    fun replaceDockableFragmentWithAnimation(frag: BaseDockFragment?) {
        val transaction = supportFragmentManager
            .beginTransaction()
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
        transaction.replace(getDockFrameLayoutId(), frag!!)
        transaction
            .addToBackStack(
                if (supportFragmentManager.backStackEntryCount == 0) KEY_FRAG_FIRST else null).commit()
    }

    fun addDockableFragment(frag: BaseFragment?) {
        val transaction = supportFragmentManager
            .beginTransaction()
        transaction.add(getDockFrameLayoutId(), frag!!)
        transaction
            .addToBackStack(
                if (supportFragmentManager.backStackEntryCount == 0) KEY_FRAG_FIRST else null).commit()
    }



    abstract fun showErrorMessage(message: String)
    abstract fun showSuccessMessage(message: String)

    fun getUserViewModel(): UserViewModel {
        return userViewModel
    }

    override fun onStarted() {
        TODO("Not yet implemented")
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        TODO("Not yet implemented")
    }

    override fun onFailure(message: String, tag: String) {
        TODO("Not yet implemented")
    }

    override fun showProgressIndicator() {
        progressBarDialog = ProgressDialog()
        progressBarDialog.showDialog(
            supportFragmentManager,
            DockActivity::class.java.simpleName
        )
    }

    override fun hideProgressIndicator() {
        if (this::progressBarDialog.isInitialized && progressBarDialog.isAdded ) {
            progressBarDialog.dismiss()
        }
    }

    abstract fun closeDrawer()
    abstract fun navigateToFragment(@IdRes id: Int, args: Bundle? = null)

}