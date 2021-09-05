package com.example.abl.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.activity.WelcomeActivity
import com.example.abl.base.BaseDockFragment
import com.example.abl.constant.Constants
import com.example.abl.databinding.FragmentWelcomeBinding
import com.example.abl.location.ForegroundOnlyLocationService
import com.example.abl.location.toText
import com.example.abl.model.GenericMsgResponse
import com.example.abl.model.MarkAttendanceModel
import com.example.abl.model.ResetPwdReqResponse
import com.example.abl.model.UserDetailsResponse
import com.example.abl.utils.GsonFactory
import com.example.abl.utils.SharedPrefKeyManager
import com.google.android.gms.location.LocationServices

class WelcomeFragment : BaseDockFragment() {
    lateinit var binding: FragmentWelcomeBinding
    var latitude = ""
    var longitude = ""

    companion object {
        fun newInstance(): WelcomeFragment {
            val args = Bundle()
            val fragment = WelcomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        SharedPrefKeyManager.with(requireContext())
        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this

//        latitude = myDockActivity?.latitude!!
//        longitude = myDockActivity?.longitude!!

        binding.fab.setOnClickListener {
            if (myDockActivity?.latitude != null && myDockActivity?.longitude != null) {
                myDockActivity?.showErrorMessage("Location not found")
            }

            markAttendance("checkin", myDockActivity?.latitude ?: "0", myDockActivity?.longitude ?: "0")
            (requireActivity() as WelcomeActivity).foregroundOnlyLocationService?.subscribeToLocationUpdates()
        }

        getUserData()

        return binding.root
    }

    private fun initView() {
        binding = FragmentWelcomeBinding.inflate(layoutInflater)
    }

    fun getUserData() {
        myDockActivity?.getUserViewModel()?.uerDetails("Bearer " + sharedPrefManager.getToken())
    }

    fun markAttendance(type: String, lat: String, lng: String) {
        myDockActivity?.showProgressIndicator()
        myDockActivity?.getUserViewModel()?.markAttendance(
            MarkAttendanceModel(type, lat, lng), "Bearer " + sharedPrefManager.getToken()
        )
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        myDockActivity?.hideProgressIndicator()
        when (tag) {
            Constants.USER_DETAIL -> {
                try {
                    val user = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, UserDetailsResponse::class.java)
                    if (user != null) {
                        binding.name.text = user.first_name + " " + user.last_name
                        sharedPrefManager.setUserDetails(user)
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            Constants.MARK_ATTENDANCE -> {
                try {
                    //val attendanceResponseEnt = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, GenericMsgResponse::class.java)
                    sharedPrefManager.setShiftStart(true)
                    val welcomeIntent = Intent(context, MainActivity::class.java)
                    welcomeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(welcomeIntent)
                    activity?.finish()
                    activity?.overridePendingTransition(R.anim.bottomtotop, R.anim.toptobottom)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
        }
    }

    override fun onFailure(message: String, tag: String) {
        super.onFailure(message, tag)
        myDockActivity?.hideProgressIndicator()
    }

    /**
     * Receiver for location broadcasts from [ForegroundOnlyLocationService].
     */

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun setTitle(text: String) {
        TODO("Not yet implemented")
    }

    override fun <T> initiateListArrayAdapter(list: List<T>): ArrayAdapter<T> {
        TODO("Not yet implemented")
    }
}