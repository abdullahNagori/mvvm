package com.example.abl.activity

import android.annotation.SuppressLint
import android.content.*
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.abl.R
import com.example.abl.base.BaseDockFragment
import com.example.abl.location.ForegroundOnlyLocationService
import com.example.abl.location.toText
import com.example.abl.model.addLead.DynamicLeadsItem
import com.example.abl.progress.ProgressDialog
import com.example.abl.progress.ProgressIndicator
import com.example.abl.room.RoomHelper
import com.example.abl.utils.InternetHelper
import com.example.abl.utils.SharedPrefManager
import com.example.abl.viewModel.UserViewModel
import com.google.android.gms.location.LocationServices
import com.tapadoo.alerter.Alerter
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject


/**
 * @author Abdullah Nagori
 */


//abstract class DockActivity : DaggerAppCompatActivity(), ApiListener, ProgressIndicator {
abstract class DockActivity : DaggerAppCompatActivity(), ProgressIndicator {
    abstract fun getDockFrameLayoutId(): Int

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var sharedPrefManager: SharedPrefManager
    @Inject
    lateinit var roomHelper: RoomHelper
    @Inject
    lateinit var internetHelper: InternetHelper

    var location: Location = Location(LocationManager.GPS_PROVIDER)

     var latitude: String? = ""
     var longitude: String? = ""

    var locationManager: LocationManager? = null

    private lateinit var progressBarDialog: ProgressDialog
    private lateinit var userViewModel: UserViewModel

    private lateinit var foregroundOnlyBroadcastReceiver: ForegroundOnlyBroadcastReceiver
    var foregroundOnlyLocationService: ForegroundOnlyLocationService? = null
    private var mBound = false

    val foregroundOnlyServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as ForegroundOnlyLocationService.LocalBinder
            foregroundOnlyLocationService = binder.service
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            foregroundOnlyLocationService = null
            mBound = false
        }
    }

    override fun onStart() {
        super.onStart()

        val serviceIntent = Intent(this, ForegroundOnlyLocationService::class.java)
        bindService(serviceIntent, foregroundOnlyServiceConnection, Context.BIND_AUTO_CREATE)

    }
    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            foregroundOnlyBroadcastReceiver!!,
            IntentFilter(
                ForegroundOnlyLocationService.ACTION_FOREGROUND_ONLY_LOCATION_BROADCAST
            )
        )

    }

    override fun onPause() {
        //removeCallBacks()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
            foregroundOnlyBroadcastReceiver!!
        )
        super.onPause()
    }

    override fun onStop() {
        if (mBound) {
            unbindService(foregroundOnlyServiceConnection)
            mBound = false
        }
        super.onStop()
    }

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        foregroundOnlyBroadcastReceiver = ForegroundOnlyBroadcastReceiver()
        getLocation()

    }

    private fun initViewModels(){
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
    }

    fun hideKeyboard(view: View) {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun replaceDockableFragmentWithoutBackStack(frag: BaseDockFragment?) {
        val transaction = supportFragmentManager
            .beginTransaction()
        transaction.replace(getDockFrameLayoutId(), frag!!)
        transaction.commit()
    }

    fun getUserViewModel(): UserViewModel {
        return userViewModel
    }

    fun showDialog(customerType: String, contact: String?,customers: DynamicLeadsItem?) {
//        val factory = LayoutInflater.from(this)
//        val dialogView: View = factory.inflate(R.layout.dialog_call, null)
//        val dialog = AlertDialog.Builder(this).setCancelable(true).create()
//        dialog.setView(dialogView)
//
//        val number = dialogView.findViewById<CustomEditText>(R.id.call)
//        contact?.let {
//            number.setText(contact)
//        }
//        val btnCall = dialogView.findViewById<ImageButton>(R.id.btn_call)
//        dialog.show()
//
//
//        btnCall.setOnClickListener {
//
//            if (number.text?.length?.compareTo(11)!! < 0){
//                number.error= "invalid number!"
//            }else {
//                dialog.dismiss()
//                val intent = Intent(Intent.ACTION_DIAL)
//                intent.data = Uri.parse("tel:" + number.text)
//                val bundle = Bundle()
//                customers?.let {
//                    bundle.putParcelable(Constants.LEAD_DATA, customers)
//                }
//                bundle.putString(Constants.TYPE, Constants.CALL)
//                bundle.putString(Constants.CUSTOMER_TYPE, customerType)
//                bundle.putString("number", number.text.toString())
//                navigateToFragment(R.id.checkInFormFragment, bundle)
//                startActivity(intent)
//            }
//        }
//        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
    }

    open fun showErrorMessage(message: String) {
        Alerter.create(this)
            .setTitle(getString(R.string.error))
            .setText(message)
            .setDuration(5000)
            .setIcon(R.drawable.ic_close)
            .setBackgroundColorRes(R.color.error_color)
            .enableSwipeToDismiss()
            .show()
    }

    open fun showSuccessMessage(message: String) {
        Alerter.create(this)
            .setTitle(getString(R.string.success))
            .setText(message)
            .setDuration(5000)
            .setIcon(R.drawable.ic_close)
            .setBackgroundColorRes(R.color.banner_green_color)
            .enableSwipeToDismiss()
            .show()
    }

    open fun onSuccessResponse(liveData: LiveData<String>, tag: String) {
        this.hideProgressIndicator()
    }

    open fun onFailureResponse(message: String, tag: String) {
        this.hideProgressIndicator()
        this.showErrorMessage(message)
    }

    fun <T : Iterable<*>?> nullGuard (item: T?): T {
        return (item ?: Collections.EMPTY_LIST) as T
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

    private fun logResultsToScreen(output: String) {
        Log.i("Foreground", output)
    }

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

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        LocationServices.getFusedLocationProviderClient(this).lastLocation.addOnSuccessListener {
            try {
                if (it == null) {
                    getLocation()
                }
                latitude = it.latitude.toString()
                longitude = it.longitude.toString()
                Log.i("CurrentLocation", "Your Location: \nLatitude: $latitude\nLongitude: $longitude")
            } catch (e: java.lang.Exception) {
            }
        }

    }
}