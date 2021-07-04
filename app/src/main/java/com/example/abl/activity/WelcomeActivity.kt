package com.example.abl.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.constant.Constants
import com.example.abl.databinding.ActivityWelcomeBinding
import com.example.abl.model.*
import com.example.abl.utils.GsonFactory

class WelcomeActivity : DockActivity() {

    lateinit var binding: ActivityWelcomeBinding

    override fun getDockFrameLayoutId(): Int {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        initView()
        logoAnimation()
        fabAnimation()
        getUserData()


    }

    override fun showErrorMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun showSuccessMessage(message: String) {
        TODO("Not yet implemented")
    }

    private fun initView(){
        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        binding.fab.setOnClickListener {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
            Log.d("Exception", "test")
            markAttendance("checkin","23.34", "50.56")
        }
    }

    private fun logoAnimation(){
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            binding.imgLogo.visibility = View.VISIBLE
            val anim = AnimationUtils.loadAnimation(this, R.anim.toptobottom)
            binding.imgLogo.startAnimation(anim)
        }, 700)
    }

    private fun fabAnimation() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            binding.fab.visibility = View.VISIBLE
            val anim = AnimationUtils.loadAnimation(this, R.anim.righttoleft)
            binding.fab.startAnimation(anim)
        }, 700)
    }

    fun getUserData(){
        getUserViewModel().uerDetails("Bearer "+sharedPrefManager.getToken())
    }

    fun markAttendance(type: String, lat: String, lng: String){
        getUserViewModel().markAttendance(MarkAttendanceModel(type,lat,lng),"Bearer "+sharedPrefManager.getToken())
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.USER_DETAIL -> {
                try {
                    Log.d("liveDataValue", liveData.value.toString())
                    val userDetailResponseEnt = GsonFactory.getConfiguredGson()
                        ?.fromJson(liveData.value, UserDetailsResponse::class.java)
                    binding.name.text = userDetailResponseEnt?.first_name

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            Constants.MARK_ATTENDANCE -> {
                try {
                    Log.d("liveDataValue", liveData.value.toString())
                    val attendanceResponseEnt = GsonFactory.getConfiguredGson()
                        ?.fromJson(liveData.value, GenericMsgResponse::class.java)
                    Log.d("AttendanceResponse", attendanceResponseEnt?.message.toString())

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
        }
    }

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        TODO("Not yet implemented")
    }
}