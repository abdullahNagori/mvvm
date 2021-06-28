package com.example.abl.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.abl.R
import com.example.abl.base.BaseFragment
import com.example.abl.common.LoadingListener
import com.example.abl.utils.SharedPrefManager
import com.example.abl.viewModel.UserViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * @author Abdullah Nagori
 */


abstract class DockActivity : DaggerAppCompatActivity(), LoadingListener {
    abstract fun getDockFrameLayoutId(): Int

    val KEY_FRAG_FIRST = "firstFrag"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
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

    fun replaceDockableFragmentWithoutBackStack(frag: BaseFragment?) {
        val transaction = supportFragmentManager
            .beginTransaction()
        transaction.replace(getDockFrameLayoutId(), frag!!)
        transaction.commit()
    }

    fun replaceDockableFragmentWithAnimation(frag: BaseFragment?) {
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

}