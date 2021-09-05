package com.example.abl.location

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.abl.R
import com.example.abl.activity.DockActivity
import com.example.abl.activity.MainActivity
import com.example.abl.activity.WelcomeActivity
import com.example.abl.model.UserLocation
import com.example.abl.room.ABLDatabase
import com.example.abl.room.RoomHelper
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 * Service tracks location when requested and updates Activity via binding. If Activity is
 * stopped/unbinds and tracking is enabled, the service promotes itself to a foreground service to
 * insure location updates aren't interrupted.
 *
 * For apps running in the background on O+ devices, location is computed much less than previous
 * versions. Please reference documentation for details.
 */
class ForegroundOnlyLocationService : Service() {
    /*
     * Checks whether the bound activity has really gone away (foreground service with notification
     * created) or simply orientation change (no-op).
     */
    private var configurationChange = false

    private var serviceRunningInForeground = false

    private val localBinder = LocalBinder()
    private var mLocation: Location? = null
    private var repo: RoomHelper? = null
    private lateinit var context: Context
    private lateinit var ablDatabase: ABLDatabase
    private lateinit var notificationManager: NotificationManager

    // TODO: Step 1.1, Review variables (no changes).
    // FusedLocationProviderClient - Main class for receiving location updates.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // LocationRequest - Requirements for the location updates, i.e., how often you should receive
    // updates, the priority, etc.
    private lateinit var locationRequest: LocationRequest

    // LocationCallback - Called when FusedLocationProviderClient has a new Location.
    private lateinit var locationCallback: LocationCallback

    private lateinit var shiftTime: String

    // Used only for local storage of the last known location. Usually, this would be saved to your
    // database, but because this is a simplified sample without a full database, we only need the
    // last location to create a Notification if the user navigates away from the app.
    private lateinit var currentLocation: Location

    override fun onCreate() {
        Log.d(TAG, "onCreate()")

        context = this
//        apiServices = Client.SERVICE_INSTANCE
//        daoAccess = DAOAccess
        ablDatabase = ABLDatabase.getInstance(context)
        repo = RoomHelper(ablDatabase.leadDao(), ablDatabase)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        locationRequest = LocationRequest().apply {

            // Sets the desired interval for active location updates. This interval is inexact. You
            // may not receive updates at all if no location sources are available, or you may
            // receive them less frequently than requested. You may also receive updates more
            // frequently than requested if other applications are requesting location at a more
            // frequent interval.
            //
            // IMPORTANT NOTE: Apps running on Android 8.0 and higher devices (regardless of
            // targetSdkVersion) may receive updates less frequently than this interval when the app
            // is no longer in the foreground

            //   interval = TimeUnit.SECONDS.toMillis(60)
            interval = TimeUnit.SECONDS.toMillis(10)

            // Sets the fastest rate for active location updates. This interval is exact, and your
            // application will never receive updates more frequently than this value.

            // fastestInterval = TimeUnit.SECONDS.toMillis(30)
            fastestInterval = TimeUnit.SECONDS.toMillis(15)

            // Sets the maximum time when batched location updates are delivered. Updates may be
            // delivered sooner than this interval.

            maxWaitTime = TimeUnit.MINUTES.toMillis(1)

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }
        // TODO: Step 1.4, Initialize the LocationCallback.
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                if (locationResult?.lastLocation != null) {

                    // Normally, you want to save a new location to a database. We are simplifying
                    // things a bit and just saving it as a local variable, as we only need it again
                    // if a Notification is created (when user navigates away from app).

                    currentLocation = locationResult.lastLocation
                    onNewLocation(currentLocation)

                    // Updates notification content if this service is running as a foreground
                    // service.
                    if (serviceRunningInForeground) {
                        notificationManager.notify(
                            NOTIFICATION_ID,
                            generateNotification()
                        )
                    }
                } else {
                    Log.d(TAG, "Location information isn't available.")

                }

            }
        }
    }


    private fun onNewLocation(location: Location) {
        Log.d(TAG, "New location: $location")
        mLocation = location
        val locationDevice = UserLocation()
        locationDevice.latitude = location.latitude
        locationDevice.longitude = location.longitude
        locationDevice.location_time = (System.currentTimeMillis() / 1000).toString()

//        val locationDevice = ArrayList<UserLocation>()
//        val locationArray = UserLocation(location.latitude.toString(), location.longitude.toString())
//        locationDevice.add(locationArray)
        Log.d(TAG, location.latitude.toString())
        GlobalScope.launch {
            insert(locationDevice)
        }

        // Notify anyone listening for broadcasts about the new location.
        val intent = Intent(ACTION_FOREGROUND_ONLY_LOCATION_BROADCAST)
        intent.putExtra(EXTRA_LOCATION, currentLocation)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }

    private fun insert(locationDevice: UserLocation) {
        Log.d(TAG, "xxDB")

        ablDatabase.leadDao().insertLocation(locationDevice)

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand()")

        val cancelLocationTrackingFromNotification =
            intent.getBooleanExtra(EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION, false)

        if (cancelLocationTrackingFromNotification) {
            unsubscribeToLocationUpdates()
            stopSelf()
        }
        // Tells the system not to recreate the service after it's been killed.
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind()")

        // MainActivity (client) comes into foreground and binds to service, so the service can
        // become a background services.
        stopForeground(true)
        serviceRunningInForeground = false
        configurationChange = false
        return localBinder
    }

    override fun onRebind(intent: Intent) {
        Log.d(TAG, "onRebind()")

        // MainActivity (client) returns to the foreground and rebinds to service, so the service
        // can become a background services.
        stopForeground(true)
        serviceRunningInForeground = false
        configurationChange = false
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d(TAG, "onUnbind()")

        // MainActivity (client) leaves foreground, so service needs to become a foreground service
        // to maintain the 'while-in-use' label.
        // NOTE: If this method is called due to a configuration change in MainActivity,
        // we do nothing.
        if (!configurationChange && SharedPreferenceUtil.getLocationTrackingPref(this)) {
            Log.d(TAG, "Start foreground service")
            val notification = generateNotification()
            startForeground(NOTIFICATION_ID, notification)

            serviceRunningInForeground = true


        }

        // Ensures onRebind() is called if MainActivity (client) rebinds.
        return true
    }


    override fun onDestroy() {
        Log.d(TAG, "onDestroy()")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configurationChange = true
    }

    fun subscribeToLocationUpdates() {
        Log.d(TAG, "subscribeToLocationUpdates()")

        SharedPreferenceUtil.saveLocationTrackingPref(this, true)

        // Binding to this service doesn't actually trigger onStartCommand(). That is needed to
        // ensure this Service can be promoted to a foreground service, i.e., the service needs to
        // be officially started (which we do here).
        startService(Intent(applicationContext, ForegroundOnlyLocationService::class.java))
        try {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            )
            {
                return
            }
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )

        } catch (unlikely: Exception) {
            SharedPreferenceUtil.saveLocationTrackingPref(this, false)
            Log.e(TAG, "Lost location permissions. Couldn't remove updates. $unlikely")
        }
    }

    fun unsubscribeToLocationUpdates() {
        Log.d(TAG, "unsubscribeToLocationUpdates()")

        try {
            // TODO: Step 1.6, Unsubscribe to location changes.

            val removeTask = fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            removeTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Location Callback removed.")
                    stopSelf()
                } else {
                    Log.d(TAG, "Failed to remove Location Callback.")
                }
            }
            SharedPreferenceUtil.saveLocationTrackingPref(this, false)

        } catch (unlikely: SecurityException) {
            SharedPreferenceUtil.saveLocationTrackingPref(this, true)
            Log.e(TAG, "Lost location permissions. Couldn't remove updates. $unlikely")
        }
    }

    /*
     * Generates a BIG_TEXT_STYLE Notification that represent latest location.
     */
    private fun generateNotification(): Notification {
        Log.d(TAG, "generateNotification()")

        // Main steps for building a BIG_TEXT_STYLE notification:
        //      0. Get data
        //      1. Create Notification Channel for O+
        //      2. Build the BIG_TEXT_STYLE
        //      3. Set up Intent / Pending Intent for notification
        //      4. Build and issue the notification

        // 0. Get data
        //    val mainNotificationText = location?.toText() ?: getString(R.string.no_location_text)
        val titleText = getString(com.example.abl.R.string.app_name)

        // 1. Create Notification Channel for O+ and beyond devices (26+).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, titleText, NotificationManager.IMPORTANCE_DEFAULT
            )

            // Adds NotificationChannel to system. Attempting to create an
            // existing notification channel with its original values performs
            // no operation, so it's safe to perform the below sequence.
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // 2. Build the BIG_TEXT_STYLE.
        val bigTextStyle = NotificationCompat.BigTextStyle()
            //  .bigText(mainNotificationText)
            .setBigContentTitle(titleText)

        // 3. Set up main Intent/Pending Intents for notification.
        val launchActivityIntent = Intent(this, MainActivity::class.java)

        val cancelIntent = Intent(this, ForegroundOnlyLocationService::class.java)
        cancelIntent.putExtra(EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION, true)

        val servicePendingIntent = PendingIntent.getService(
            this, 0, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val activityPendingIntent = PendingIntent.getActivity(
            this, 0, launchActivityIntent, 0
        )

        // 4. Build and issue the notification.
        // Notification Channel Id is ignored for Android pre O (26).
        val notificationCompatBuilder =
            NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)

        return notificationCompatBuilder
            .setStyle(bigTextStyle)
            .setContentTitle(titleText)
            // .setContentText(mainNotificationText)
            .setSmallIcon(com.example.abl.R.mipmap.ic_launcher_abl)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setOngoing(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(
                com.example.abl.R.mipmap.ic_launcher_abl,
                getString(com.example.abl.R.string.launch_activity),
                activityPendingIntent
            )
            .addAction(
                com.example.abl.R.drawable.ic_close,
                getString(R.string.stop_location_updates_button_text),
                servicePendingIntent
            )
            .build()
    }

    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        internal val service: ForegroundOnlyLocationService
            get() = this@ForegroundOnlyLocationService
    }

    companion object {
        private const val TAG = "ForegroundLocation"

        private const val PACKAGE_NAME = "com.example.abl"

        internal const val ACTION_FOREGROUND_ONLY_LOCATION_BROADCAST =
            "$PACKAGE_NAME.action.FOREGROUND_ONLY_LOCATION_BROADCAST"

        internal const val EXTRA_LOCATION = "$PACKAGE_NAME.extra.LOCATION"

        private const val EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION =
            "$PACKAGE_NAME.extra.CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION"

        private const val NOTIFICATION_ID = 12345678
        private const val NOTIFICATION_ID_Shift = 123

        private const val NOTIFICATION_CHANNEL_ID = "while_in_use_channel_01"
        private const val NOTIFICATION_CHANNEL_ID_2 = "while_in_use_channel_02"
    }
}