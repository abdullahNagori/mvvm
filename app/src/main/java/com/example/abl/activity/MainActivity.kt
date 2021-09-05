package com.example.abl.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import butterknife.ButterKnife
import butterknife.Unbinder
import com.deepakkumardk.kontactpickerlib.KontactPicker
import com.deepakkumardk.kontactpickerlib.model.KontactPickerItem
import com.deepakkumardk.kontactpickerlib.model.SelectionMode
import com.example.abl.R
import com.example.abl.adapter.ExpandableListAdapter
import com.example.abl.constant.Constants
import com.example.abl.databinding.ActivityMainBinding
import com.example.abl.model.*
import com.example.abl.utils.*
import com.example.abl.utils.Schedulers.LocationWorkManager.LocationWorker
import com.example.abl.utils.Schedulers.UploadWorkManager.UploadWorker
import com.example.abl.viewModel.coroutine.CoroutineViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_menu_shortcut.*
import kotlinx.android.synthetic.main.nav_header_main.*
import okhttp3.ResponseBody
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.set


class MainActivity : DockActivity() {
    lateinit var number: CustomEditText

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var unbinder: Unbinder
        lateinit var navController: NavController
        lateinit var drawerLayout: DrawerLayout
    }

    lateinit var binding: ActivityMainBinding
    private lateinit var contentView: ConstraintLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    val END_SCALE = 0.7f
    private lateinit var actionBarMenu: Menu
    private lateinit var switchAB: SwitchCompat
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var viewModel: CoroutineViewModel
    lateinit var listDataChild: HashMap<String, List<String>>
    lateinit var listDataHeader: ArrayList<String>
    private var x1 = 0f
    private var x2 = 0f
    val MIN_DISTANCE = 60
    //val childLeadMap: HashMap<String, String> = HashMap()

    private var companyLeadSource: List<CompanyLeadSource> = emptyList()

    override fun getDockFrameLayoutId(): Int {
        return R.id.container
    }

    // Monitors connection to the while-in-use service.
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        unbinder = ButterKnife.bind(this)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_main)

        name.text =
            sharedPrefManager.getUserDetails()?.first_name + " " + sharedPrefManager.getUserDetails()?.last_name

        initView()
        setGesture()
        sendUserTracking()

        viewModel = ViewModelProvider(this, viewModelFactory).get(CoroutineViewModel::class.java)

    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.nav_host_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        actionBarMenu = menu

        val item = menu.findItem(R.id.myswitch) as MenuItem


        actionBarMenu.findItem(R.id.action_notification).setOnMenuItemClickListener {
//            if (SharedPrefKeyManager.get<Boolean>(Constants.IS_SHIFT) == true)
//                navController.navigate(R.id.nav_notification)
//            else
//                showErrorMessage(getString(R.string.start_your_shift))
            true
        }


        item.setActionView(R.layout.switch_layout)
        switchAB = item.actionView.findViewById(R.id.switchAB)
        sharedPreferences = this.getSharedPreferences("SharedPrefs", MODE_PRIVATE)

        if (switchAB.isChecked) {
            Log.i("xxChecked", "check")
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                foregroundOnlyLocationService?.subscribeToLocationUpdates()
            }, 120000)
        }
        switchAB.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
            } else {
                Log.i("xxChecked", "uncheck")
                sharedPrefManager.setShiftStart(false)
                //   foregroundOnlyLocationService!!.unsubscribeToLocationUpdates()
                startActivity(Intent(this, WelcomeActivity::class.java))
                foregroundOnlyLocationService?.unsubscribeToLocationUpdates()
                // LoginActivity.navController.navigate()
                // Navigation.findNavController().navigate(R.id.nav_graph_actFirstActvity)

            }

        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun initView() {
        drawerLayout = binding.drawerLayout

        setSupportActionBar(findViewById(R.id.toolBar))

        contentView = binding.appBarMain.content
        navController = findNavController(R.id.nav_host_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        animateNavigationDrawer(drawerLayout)

        prepareSideMenu()
    }

    private fun animateNavigationDrawer(drawerLayout: DrawerLayout) {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);

        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                // Scale the View based on current slide offset
                val diffScaledOffset: Float = slideOffset * (1 - END_SCALE)
                val offsetScale = 1 - diffScaledOffset
                contentView.scaleX = offsetScale
                contentView.scaleY = offsetScale


                // Translate the View, accounting for the scaled width
                val xOffset: Float = drawerView.width * slideOffset
                val xOffsetDiff: Float = contentView.width * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetDiff
                contentView.translationX = xTranslation

            }
        })
    }

    private fun fragmentClickEvent(itemString: String) {
        when (itemString) {

            Constants.DASHBOARD -> {
                closeDrawer()
            }

            Constants.PORTFOLIO -> {
                navigateToFragment(R.id.action_nav_home_to_nav_profile)
                closeDrawer()
            }

            Constants.CALL_LOG -> {
                navigateToFragment(R.id.action_nav_home_to_call_logs)
                closeDrawer()
            }

            Constants.VISIT_LOG -> {
                navigateToFragment(R.id.action_nav_home_to_visit_logs)
                closeDrawer()
            }

            Constants.COMPANY_PROVIDED_LEADS -> {
                navigateToFragment(R.id.action_nav_home_to_company_provided_leads)
                closeDrawer()
            }

            Constants.MARKETING_COLLATERAL -> {
                navigateToFragment(R.id.action_nav_home_to_nav_marketing_collateral)
                closeDrawer()
            }

            Constants.SALES_PIPELINE -> {
                navigateToFragment(R.id.action_nav_home_to_company_provided_leads)
                closeDrawer()
            }

            Constants.NOTIFICATIONS -> {
                navigateToFragment(R.id.action_nav_home_to_nav_notification)
                closeDrawer()
            }

            Constants.CALCULATOR -> {
                navigateToFragment(R.id.action_nav_home_to_nav_calculator)
                closeDrawer()
            }

            Constants.TRAINING -> {
                navigateToFragment(R.id.action_nav_home_to_nav_training)
                closeDrawer()
            }

            Constants.TRACKING -> {
                navigateToFragment(R.id.action_nav_home_to_nav_tracking)
                closeDrawer()
            }

            Constants.LOGOUT -> {
                sharedPrefManager.logout()
                foregroundOnlyLocationService?.unsubscribeToLocationUpdates()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                closeDrawer()
            }
        }
    }

    private fun prepareSideMenu() {

        listDataHeader = ArrayList<String>()
        listDataChild = HashMap<String, List<String>>()
        val icons = ArrayList<Int>()

        icons.add(R.drawable.ic_dashboard) //0
        icons.add(R.drawable.ic_portfolio) //1
        icons.add(R.drawable.ic_reports) //2
        icons.add(R.drawable.ic_sales) //3
        icons.add(R.drawable.ic_lead) // 4
        icons.add(R.drawable.ic_marketing) //5
        icons.add(R.drawable.ic_calculator) //6
        icons.add(R.drawable.ic_training) //7
        icons.add(R.drawable.ic_leaderboard) //8
        icons.add(R.drawable.ic_notification_drawer) //9
        icons.add(R.drawable.ic_forgot) //10
        icons.add(R.drawable.ic_joinvisit) //11
        icons.add(R.drawable.ic_marketing) //12
        icons.add(R.drawable.ic_logout) //13

        listDataHeader.add(Constants.DASHBOARD) //0
        listDataHeader.add(Constants.PORTFOLIO) //1
        listDataHeader.add(Constants.REPORT) //2
        listDataHeader.add(Constants.SALES_MANAGEMENT) //3
        listDataHeader.add(Constants.LEAD_MANAGEMENT) //4
        listDataHeader.add(Constants.MARKETING_COLLATERAL) //5
        listDataHeader.add(Constants.CALCULATOR) //6
        listDataHeader.add(Constants.TRAINING) //7
        listDataHeader.add(Constants.LEADER_BOARD) //8
        listDataHeader.add(Constants.NOTIFICATIONS) //9
        listDataHeader.add(Constants.PASSWORD_CHANGE) //10
        listDataHeader.add(Constants.JOIN_VISIT) //11
        listDataHeader.add(Constants.TRACKING) //12
        listDataHeader.add(Constants.LOGOUT) //13

        val listAdapter = ExpandableListAdapter(
            this,
            listDataHeader,
            listDataChild,
            icons
        )

        // Sales management child nodes
        val salesManagement: MutableList<String> = ArrayList()
        salesManagement.add(Constants.CALL_LOG)
        salesManagement.add(Constants.VISIT_LOG)
        listDataChild[listDataHeader[3]] = salesManagement

        // Lead management child nodes
        setLeadManagementChildNodes()

        // setting list adapter
        binding.sideLayout.lvExp.setAdapter(listAdapter)

        // Listview on child click listener
        binding.sideLayout.lvExp.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            var str = listDataChild[listDataHeader[groupPosition]]!![childPosition]
            if (groupPosition == 4) {
                val bundle = Bundle()
                bundle.putString(Constants.LEAD_SOURCE_DATA, GsonFactory.getConfiguredGson()?.toJson(companyLeadSource[childPosition]))
                navigateToFragment(R.id.action_nav_home_to_nav_crm, bundle)
                closeDrawer()
            } else {
                fragmentClickEvent(str)
            }
            false
        }

        // Listview parent node click listener
        binding.sideLayout.lvExp.setOnGroupExpandListener { groupPosition: Int ->
            fragmentClickEvent(listDataHeader[groupPosition])
        }
    }

    private fun setLeadManagementChildNodes() {
        companyLeadSource = sharedPrefManager.getLeadSource() ?: emptyList()
        listDataChild[listDataHeader[4]] = companyLeadSource.map { it.name }
    }

    private fun callLead() {
        showDialog_new(Constants.NTB, null, null)
    }

    fun dropDownMenu(view: View) {
        showOrHide()
        binding.appBarMain.sideMenu.sync.setOnClickListener(::onCLickEvent)
        binding.appBarMain.sideMenu.upload.setOnClickListener(::onCLickEvent)
        binding.appBarMain.sideMenu.coldCalling.setOnClickListener(::onCLickEvent)
        binding.appBarMain.sideMenu.addLead.setOnClickListener(::onCLickEvent)
        binding.appBarMain.sideMenu.followup.setOnClickListener(::onCLickEvent)
        binding.appBarMain.sideMenu.close.setOnClickListener(::onCLickEvent)
    }

    private fun onCLickEvent(view: View) {
        showOrHide()
        when (view.id) {
            R.id.sync -> {
                getSyncData()
                //getLeads()
            }
            R.id.upload -> {
                sendLeadData()
                //Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
            }
            R.id.cold_calling -> callLead()
            R.id.addLead -> {
                val bundle = Bundle()
                bundle.putString(Constants.VISIT_TYPE, Constants.VISIT)
                navigateToFragment(R.id.nav_visit)
            }
            R.id.followup -> {
                navigateToFragment(R.id.followup_fragment)
            }
            R.id.close -> {
                goneWithAnimation(binding.appBarMain.sideMenu.root)
                visibleWithAnimation(binding.appBarMain.dropdownMenu)
            }
        }
    }

    private fun showOrHide() {
        if (!binding.appBarMain.sideMenu.root.isVisible) {
            goneWithAnimation(binding.appBarMain.dropdownMenu)
            visibleWithAnimation(binding.appBarMain.sideMenu.root)
        } else {
            visibleWithAnimation(binding.appBarMain.dropdownMenu)
            goneWithAnimation(binding.appBarMain.sideMenu.root)
        }
    }

    private fun visibleWithAnimation(view: View) {
        view.visibility = View.VISIBLE
        view.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.slide_in_right
            )
        )
    }

    private fun goneWithAnimation(view: View) {
        view.visibility = View.GONE
        view.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.slide_out_right
            )
        )
    }

    private fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setGesture() {
        binding.appBarMain.sideMenu.root.setOnTouchListener { p0, p1 ->
            if (p1 != null) {
                when (p1.action) {
                    MotionEvent.ACTION_DOWN -> x1 = p1.x
                    MotionEvent.ACTION_UP -> {
                        x2 = p1.x
                        val deltaX: Float = x2 - x1
                        if (Math.abs(deltaX) > MIN_DISTANCE) {
                            showOrHide()
                        }
                    }
                }
            }
            true
        }
    }

    private fun navigateToFragment(@IdRes id: Int, args: Bundle? = null) {
        if (args != null) {
            navController.navigate(id, args)
            return
        }
        navController.navigate(id)
    }

    fun showDialog_new(customerType: String, contact: String?, customers: DynamicLeadsItem?) {

        val factory = LayoutInflater.from(this)
        val dialogView: View = factory.inflate(R.layout.dialog_call, null)
        val dialog = AlertDialog.Builder(this).setCancelable(true).create()
        dialog.setView(dialogView)

        number = dialogView.findViewById<CustomEditText>(R.id.call)
        contact?.let {
            number.setText(contact)
        }

        number.setDrawableClickListener(object : DrawableClickListener {
            override fun onClick(target: DrawableClickListener.DrawablePosition?) {
                when (target) {
                    DrawableClickListener.DrawablePosition.RIGHT -> {
                        val item = KontactPickerItem().apply {
                            debugMode = true
                            selectionMode = SelectionMode.Single
                            textBgColor =
                                ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)
                        }
                        KontactPicker().startPickerForResult(this@MainActivity, item, 3000)
                    }
                    else -> {
                    }
                }
            }
        })
        val btnCall = dialogView.findViewById<ImageButton>(R.id.btn_call)
        dialog.show()


        btnCall.setOnClickListener {

            if (number.text?.length?.compareTo(11)!! < 0) {
                number.error = "invalid number!"
            } else {
                dialog.dismiss()
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:" + number.text)
                val bundle = Bundle()
                customers?.let {
                    // bundle.putParcelable(Constants.LEAD_DATA, customers)
                }
                bundle.putString(Constants.VISIT_TYPE, Constants.CALL)
                bundle.putString(Constants.CUSTOMER_TYPE, customerType)
                bundle.putString("number", number.text.toString())
                navigateToFragment(R.id.addLeadFragment, bundle)
                startActivity(intent)
            }
        }
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 3000) {
            val list = KontactPicker.getSelectedKontacts(data) //ArrayList<MyContacts>
            if (list!!.isNotEmpty()) {
                list?.get(0)?.contactNumber?.let {
//                    val intent = Intent(Intent.ACTION_CALL)
//                    intent.data = Uri.parse(it)
                    number.setText(it)
                }
            }
            //Log.i("xxNumber", list[0].)
        }
    }

    private fun getSyncData() {
        if ( roomHelper.checkUnSyncLeadData().isNotEmpty() || roomHelper.checkUnSyncCheckInData().isNotEmpty()) {
            showErrorMessage(getString(R.string.un_synced_msg))
        }
        else {
            this.showProgressIndicator()
            viewModel.getLOV().observe(this) {
                this.hideProgressIndicator()
                if (it.lovResponse != null && it.lovResponse.company_lead_source.isNotEmpty() && it.lovResponse.company_lead_status.isNotEmpty()) {
                    processData(it.lovResponse, it.dynamicList, it.visitCallResponse)
                } else {
                    this.showErrorMessage("Failed to sync data. Please try again")
                }
            }
        }
    }

    private fun processData(lovResponse: LovResponse, dynamicLeadsItem: ArrayList<DynamicLeadsItem>?, visitsCallResponseItem:ArrayList<CheckinModel>?) {
        sharedPrefManager.setLeadStatus(lovResponse.company_lead_status)
        sharedPrefManager.setCompanyProducts(lovResponse.company_products)
        sharedPrefManager.setVisitStatus(lovResponse.company_visit_status)
        sharedPrefManager.setLeadSource(lovResponse.company_lead_source)

        // Set leads data in local DB
        if (dynamicLeadsItem != null) {
            roomHelper.deleteLeadData()
            roomHelper.insertLeadData(dynamicLeadsItem)
        }

        // Set checkIn data in local DB
        if (visitsCallResponseItem != null) {
            roomHelper.deleteCheckInData()
            roomHelper.insertVisitCallData(visitsCallResponseItem)
        }

        prepareSideMenu()
    }

    private fun sendUserTracking() {

        val workManager = WorkManager.getInstance(application)
        val constraints: androidx.work.Constraints = androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        try {

            // One time request only
//            val uploadWorkRequest = OneTimeWorkRequestBuilder<LocationWorker>().build()
//            WorkManager.getInstance().enqueue(uploadWorkRequest)

            // Periodic Request
            val periodicSyncDataWork = PeriodicWorkRequest.Builder(
                LocationWorker::class.java,
                30,
                TimeUnit.MINUTES,
                15,
                TimeUnit.MINUTES
            )
                .addTag(Constants.SYNC_LOCATION)
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
            workManager.enqueueUniquePeriodicWork(
                Constants.SYNC_UPLOADED,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicSyncDataWork
            )
        } catch (e: Exception) {
            Log.i("LocationWorkerException", e.message.toString())
        }
    }

    private fun sendLeadData() {
        try {
            val workManager = WorkManager.getInstance(application)
            val uploadDataConstraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                .setConstraints(uploadDataConstraints)
                .build()

            this.showProgressIndicator()

            workManager.enqueue(uploadWorkRequest)

            workManager.getWorkInfoByIdLiveData(uploadWorkRequest.id).observe(this, androidx.lifecycle.Observer { workInfo ->
                this.hideProgressIndicator()
                this.showSuccessMessage("Data uploaded successfully")
            })

        } catch (e: Exception) {
            Log.i("UploadWorkerLocation", e.message.toString())
        }
    }
}