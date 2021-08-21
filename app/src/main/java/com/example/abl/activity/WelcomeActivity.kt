package com.example.abl.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abl.R
import com.example.abl.constant.Constants
import com.example.abl.databinding.ActivityMainBinding
import com.example.abl.databinding.ActivityWelcomeBinding
import com.example.abl.fragment.WelcomeFragment
import com.example.abl.model.*
import com.example.abl.network.Api
import com.example.abl.network.ApiListener
import com.example.abl.utils.GsonFactory
import com.example.abl.utils.SharedPrefKeyManager
import com.example.abl.viewModel.UserViewModel
import com.tapadoo.alerter.Alerter
import dagger.android.AndroidInjection.inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : DockActivity() {

    lateinit var binding: ActivityWelcomeBinding
    private val viewModel by inject<UserViewModel>()

    override fun getDockFrameLayoutId(): Int {
        return R.id.container
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SharedPrefKeyManager.with(this)
        initFragment()
    }

    private fun initView(){
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
    }

    private fun initFragment() {
        replaceDockableFragmentWithoutBackStack(WelcomeFragment())
    }

    fun getUserData(){
     //   getUserViewModel().uerDetails("Bearer "+sharedPrefManager.getToken())

    }
    fun markAttendance(type: String, lat: String, lng: String){
        getUserViewModel().markAttendance(MarkAttendanceModel(type,lat,lng),"Bearer "+sharedPrefManager.getToken())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}