package com.uhfsolutions.abl.activity

import android.annotation.SuppressLint
import android.content.*
import android.location.Location
import android.os.Bundle
import android.util.Log
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.databinding.ActivityWelcomeBinding
import com.uhfsolutions.abl.fragment.login.WelcomeFragment
import com.uhfsolutions.abl.location.ForegroundOnlyLocationService
import com.uhfsolutions.abl.location.toText
import com.uhfsolutions.abl.model.*
import com.uhfsolutions.abl.utils.SharedPrefKeyManager

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