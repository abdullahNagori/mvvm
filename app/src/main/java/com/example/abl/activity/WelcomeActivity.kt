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
       // getUserViewModel().apiListener = this
      //  initView()
      //  logoAnimation()
       // fabAnimation()
      //  getUserData()
      //  binding.fab.setOnClickListener(this);
//        binding.fab.setOnClickListener {
//            SharedPrefKeyManager.put(true, Constants.IS_SHIFT)
//            markAttendance("checkin", "23.45", "35.40")
//            val welcomeIntent = Intent(this, MainActivity::class.java)
//            welcomeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            startActivity(welcomeIntent)
//        }

    }

    override fun showErrorMessage(message: String) {
        Alerter.create(this)
            .setTitle(getString(R.string.error))
            .setText(message)
            .setDuration(5000)
            .setIcon(R.drawable.ic_close)
            .setBackgroundColorRes(R.color.error_color)
            .enableSwipeToDismiss()
            .show()
    }

    override fun showSuccessMessage(message: String) {
        Alerter.create(this)
            .setTitle(getString(R.string.success))
            .setText(message)
            .setDuration(5000)
            .setIcon(R.drawable.ic_close)
            .setBackgroundColorRes(R.color.banner_green_color)
            .enableSwipeToDismiss()
            .show()
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

//    override fun onSuccess(liveData: LiveData<String>, tag: String) {
//        super.onSuccess(liveData, tag)
//        when (tag) {
//            Constants.USER_DETAIL -> {
//                try {
//                    Log.d("liveDataValue", liveData.value.toString())
//                    val userDetailResponseEnt = GsonFactory.getConfiguredGson()
//                        ?.fromJson(liveData.value, UserDetailsResponse::class.java)
//                        binding.name.text = userDetailResponseEnt?.first_name
//                } catch (e: Exception) {
//                    Log.d("Exception", e.message.toString())
//                }
//            }
//
//            Constants.MARK_ATTENDANCE -> {
//                try {
//                    Log.d("liveDataValue", liveData.value.toString())
//                    val attendanceResponseEnt = GsonFactory.getConfiguredGson()
//                        ?.fromJson(liveData.value, GenericMsgResponse::class.java)
//                    Log.d("AttendanceResponse", attendanceResponseEnt?.message.toString())
//                    val welcomeIntent = Intent(this, MainActivity::class.java)
//                    welcomeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    startActivity(welcomeIntent)
//
//                } catch (e: Exception) {
//                    Log.d("Exception", e.message.toString())
//                }
//            }
//        }
//    }

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun callDialog(type: String, contact: String?, dynamicLeadsItem: DynamicLeadsItem?) {
        TODO("Not yet implemented")
    }

    override fun showPasswordchangingInstructions(text: String?) {
        TODO("Not yet implemented")
    }

//    override fun onClick(v: View?) {
//        Log.d("Exception", "test")
//    }
}