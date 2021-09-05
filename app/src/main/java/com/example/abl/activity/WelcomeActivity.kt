package com.example.abl.activity

import android.annotation.SuppressLint
import android.content.*
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.abl.R
import com.example.abl.constant.Constants
import com.example.abl.databinding.ActivityMainBinding
import com.example.abl.databinding.ActivityWelcomeBinding
import com.example.abl.fragment.WelcomeFragment
import com.example.abl.location.ForegroundOnlyLocationService
import com.example.abl.location.toText
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

    companion object {
         @SuppressLint("StaticFieldLeak")
         var foregroundOnlyLocationService: ForegroundOnlyLocationService? = null
    }

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

    private fun initFragment() {
        replaceDockableFragmentWithoutBackStack(WelcomeFragment())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun logResultsToScreen(output: String) {
        Log.i("Foreground", output)
    }

    /**
     * Receiver for location broadcasts from [ForegroundOnlyLocationService].
     */
    private inner class ForegroundOnlyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val location = intent.getParcelableExtra<Location>(
                ForegroundOnlyLocationService.EXTRA_LOCATION
            )

            if (location != null) {
                logResultsToScreen("Foreground location: ${location.toText()}")
            }
        }
    }
}