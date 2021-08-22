package com.example.abl.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import butterknife.ButterKnife
import butterknife.Unbinder
import com.deepakkumardk.kontactpickerlib.KontactPicker
import com.deepakkumardk.kontactpickerlib.model.KontactPickerItem
import com.deepakkumardk.kontactpickerlib.model.SelectionMode
import com.example.abl.R
import com.example.abl.adapter.ExpandableListAdapter
import com.example.abl.constant.Constants
import com.example.abl.databinding.ActivityMainBinding
import com.example.abl.model.DynamicLeadsItem
import com.example.abl.model.LovResponse
import com.example.abl.network.coroutine.WebResponse
import com.example.abl.utils.*
import com.example.abl.viewModel.coroutine.CoroutineViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.isNotEmpty
import kotlin.collections.set
import kotlin.collections.setOf


class MainActivity : DockActivity() {
    lateinit var number: CustomEditText
    companion object{

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

    private var x1 = 0f
    private var x2 = 0f
    val MIN_DISTANCE = 60

    override fun getDockFrameLayoutId(): Int {
        return R.id.container
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        unbinder = ButterKnife.bind(this)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_main)


        name.text = sharedPrefManager.getUserDetails()?.first_name + " " + sharedPrefManager.getUserDetails()?.last_name
        initView()
        setGesture()
        viewModel = ViewModelProvider(this, viewModelFactory).get(CoroutineViewModel::class.java)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this ,arrayOf(Manifest.permission.CALL_PHONE),1);
        }
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

//        if (SharedPrefKeyManager.get<Boolean>(Constants.IS_SHIFT) == true) {
//            switchAB.isChecked = true
//        }

        switchAB.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){

                Log.i("xxChecked", "check")
            }else{
                Log.i("xxChecked", "uncheck")
                sharedPrefManager.setShiftStart(false)
                startActivity(Intent(this, WelcomeActivity::class.java))
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

    private fun fragmentClickEvent(itemString: String?) {

        when(itemString){
            Constants.DASHBOARD -> {
            }

            Constants.PORTFOLIO -> {
                navigateToFragment(R.id.action_nav_home_to_nav_profile)
                closeDrawer()
            }

            Constants.MY_LEADS -> {
                navigateToFragment(R.id.action_nav_home_to_nav_crm)
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

            Constants.LOGOUT -> {
                sharedPrefManager.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                closeDrawer()
            }
        }
    }

    private fun prepareSideMenu() {

        val listDataHeader = ArrayList<String>()
        val listDataChild = HashMap<String, List<String>>()
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
        icons.add(R.drawable.ic_logout) //12


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
        listDataHeader.add(Constants.LOGOUT) //12



        val listAdapter = ExpandableListAdapter(
            this,
            listDataHeader,
            listDataChild,
            icons
        )

        val salesManagement: MutableList<String> = ArrayList()
        salesManagement.add(Constants.CALL_LOG)
        salesManagement.add(Constants.VISIT_LOG)

        val leadManagement: MutableList<String> = ArrayList()
        leadManagement.add(Constants.MY_LEADS)
        leadManagement.add(Constants.COMPANY_PROVIDED_LEADS)
        leadManagement.add(Constants.SALES_PIPELINE)

        listDataChild[listDataHeader[3]] = salesManagement
        listDataChild[listDataHeader[4]] = leadManagement


        // setting list adapter
        binding.sideLayout.lvExp.setAdapter(listAdapter)


        // Listview on child click listener
        binding.sideLayout.lvExp.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->

            Log.i("xxGroup", "child")
            val str = listDataChild[listDataHeader[groupPosition]]!![childPosition]
                fragmentClickEvent(str)
                closeDrawer()
            false
        }


        binding.sideLayout.lvExp.setOnGroupExpandListener { groupPosition: Int ->
            Log.i("xxGroup", "group")
            fragmentClickEvent(listDataHeader[groupPosition])
        }
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
            R.id.upload -> {Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()}
            R.id.cold_calling ->  callLead()
            R.id.addLead -> {
                val bundle = Bundle()
                bundle.putString(Constants.TYPE, Constants.VISIT)
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

    fun showDialog_new(customerType: String, contact: String?,customers: DynamicLeadsItem?) {

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
                            textBgColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)
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

            if (number.text?.length?.compareTo(11)!! < 0){
                number.error= "invalid number!"
            }else {
                dialog.dismiss()
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this ,arrayOf(Manifest.permission.CALL_PHONE),1);
                }
                else
                {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:" + number.text)
                    val bundle = Bundle()
                    customers?.let {
                        bundle.putParcelable(Constants.LEAD_DATA, customers)
                    }
                    bundle.putString(Constants.TYPE, Constants.CALL)
                    bundle.putString(Constants.CUSTOMER_TYPE, customerType)
                    bundle.putString("number", number.text.toString())
                    navigateToFragment(R.id.addLeadFragment, bundle)
                    startActivity(intent)
                }

            }
        }
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 3000) {
            val list = KontactPicker.getSelectedKontacts(data) //ArrayList<MyContacts>
            if (list!!.isNotEmpty()){
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

        viewModel.getLOV().observe(this) {
            when (it) {
                WebResponse.Loading -> {
                    showProgressIndicator()
                }
                is WebResponse.Success<*> -> {
                    hideProgressIndicator()
                    val response = it.data as LovResponse
                    sharedPrefManager.setLeadStatus(response.company_lead_status)
                }
                is WebResponse.Error -> {
                    hideProgressIndicator()
                    // showBanner(it.exception, Constant.ERROR)
                    //showBanner(getString(R.string.something_wrong), Constant.ERROR)
                }
            }
        }

        viewModel.getLeads().observe(this) {
            when (it) {
                WebResponse.Loading -> {
                    showProgressIndicator()
                }
                is WebResponse.Success<*> -> {
                    hideProgressIndicator()
                    val response = it.data as List<DynamicLeadsItem>
                    sharedPrefManager.setLeadData(response)
                }
                is WebResponse.Error -> {
                    hideProgressIndicator()
                    // showBanner(it.exception, Constant.ERROR)
                    //showBanner(getString(R.string.something_wrong), Constant.ERROR)
                }
            }
        }

    }

//    private fun getLeads() {
//        GlobalScope.launch {
//            getUserViewModel().getLeads()
//        }
//    }

    override fun onSuccessResponse(liveData: LiveData<String>, tag: String) {
        super.onSuccessResponse(liveData, tag)
        when (tag) {
            Constants.GET_LOVS -> {
                try {
                    val lovResponse = GsonFactory.getConfiguredGson()?.fromJson(liveData.value, LovResponse::class.java)
                    sharedPrefManager.setLeadStatus(lovResponse!!.company_lead_status)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
            Constants.GET_LEADS -> {
                try {
                    val listType: Type = object : TypeToken<List<DynamicLeadsItem?>?>() {}.type
                    val leads: List<DynamicLeadsItem> = Gson().fromJson<List<DynamicLeadsItem>>(liveData.value, listType)
                    sharedPrefManager.setLeadData(leads)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
        }
    }
}