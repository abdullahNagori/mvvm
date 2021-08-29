package com.example.abl.fragment

import android.Manifest
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
    var latitude = 0.0
    var longitude = 0.0


    companion object {
        fun newInstance(): WelcomeFragment {
            val args = Bundle()
            val fragment = WelcomeFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        SharedPrefKeyManager.with(requireContext())
        initView()
      //  fabAnimation()
        myDockActivity?.getUserViewModel()?.apiListener = this
        getUserData()

        binding.fab.setOnClickListener {
            markAttendance("checkin", "23.45", "35.40")
            (requireActivity() as WelcomeActivity).foregroundOnlyLocationService?.subscribeToLocationUpdates()
        }

        return binding.root
    }

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

    private fun initView() {
        binding = FragmentWelcomeBinding.inflate(layoutInflater)
    }

    private fun fabAnimation() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            binding.fab.visibility = View.VISIBLE
            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.righttoleft)
            binding.fab.startAnimation(anim)
        }, 700)
    }


    fun getUserData() {
        myDockActivity?.getUserViewModel()?.uerDetails("Bearer " + sharedPrefManager.getToken())
    }

    fun markAttendance(type: String, lat: String, lng: String) {
        myDockActivity?.getUserViewModel()?.markAttendance(
            MarkAttendanceModel(type, lat, lng),
            "Bearer " + sharedPrefManager.getToken()
        )
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        } else {
            LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation.addOnSuccessListener {
                try {
                    if(it==null){
                        getLocation()
                    }
                    latitude = it.latitude
                    longitude = it.longitude
                } catch (e: java.lang.Exception) {
                }
            }

        }
    }


    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
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
                    val attendanceResponseEnt = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, GenericMsgResponse::class.java)
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



    /**
     * Receiver for location broadcasts from [ForegroundOnlyLocationService].
     */

}