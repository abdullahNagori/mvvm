package com.uhfsolutions.abl.fragment.login

import android.annotation.SuppressLint
import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.activity.MainActivity
import com.uhfsolutions.abl.activity.WelcomeActivity
import com.uhfsolutions.abl.base.BaseDockFragment
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.databinding.FragmentWelcomeBinding
import com.uhfsolutions.abl.location.ForegroundOnlyLocationService
import com.uhfsolutions.abl.model.markAttendance.MarkAttendanceModel
import com.uhfsolutions.abl.model.userDetail.UserDetailsResponse
import com.uhfsolutions.abl.utils.GsonFactory
import com.uhfsolutions.abl.utils.SharedPrefKeyManager
import com.uhfsolutions.abl.viewModel.coroutine.CoroutineViewModel

class WelcomeFragment : BaseDockFragment() {
    lateinit var binding: FragmentWelcomeBinding
    var latitude = ""
    var longitude = ""
    var twoFactor = "yes"

    companion object {
        fun newInstance(): WelcomeFragment {
            val args = Bundle()
            val fragment = WelcomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var viewModel: CoroutineViewModel


    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this, viewModelFactory).get(CoroutineViewModel::class.java)
        SharedPrefKeyManager.with(requireContext())
        initView()
        myDockActivity?.getUserViewModel()?.apiListener = this

        twoFactor = arguments?.getString(Constants.LOGIN_TWO_FACTOR).toString()

        if (twoFactor == "no"){
            binding.imgLogo.visibility = View.GONE
        }

        binding.fab.setOnClickListener {

            if (myDockActivity?.latitude == "" && myDockActivity?.longitude == "") {
                myDockActivity?.latitude = "0.0"
                myDockActivity?.longitude = "0.0"
            }

            markAttendance("checkin", myDockActivity?.latitude.toString(), myDockActivity?.longitude.toString())
            (requireActivity() as WelcomeActivity).foregroundOnlyLocationService?.
            subscribeToLocationUpdates()
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






}