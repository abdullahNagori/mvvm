package com.example.abl.activity

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.IdRes
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.abl.R
import com.example.abl.adapter.ExpandableListAdapter
import com.example.abl.base.BaseActivity
import com.example.abl.constant.Constants
import com.example.abl.databinding.ActivityMainBinding
import com.example.abl.utils.SharedPrefKeyManager
import com.example.abl.utils.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Array.get
import java.nio.file.Paths.get
import java.util.HashMap

class MainActivity : DockActivity() {


    companion object{

        @SuppressLint("StaticFieldLeak")
        private lateinit var unbinder: Unbinder
        lateinit var navController: NavController
        lateinit var navWelcome: NavController
        lateinit var drawerLayout: DrawerLayout
    }

    lateinit var binding: ActivityMainBinding
    private lateinit var contentView: ConstraintLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    val END_SCALE = 0.7f
    private lateinit var actionBarMenu: Menu
    private lateinit var switchAB: SwitchCompat
    private lateinit var sharedPreferences: SharedPreferences
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
        navWelcome = findNavController(R.id.nav_host_welcome)

        initView()
        setGesture()
    }

    override fun showErrorMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun showSuccessMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.nav_host_main)
        navWelcome = findNavController(R.id.nav_host_welcome)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        actionBarMenu = menu

        val item = menu.findItem(R.id.myswitch) as MenuItem


        actionBarMenu.findItem(R.id.action_notification).setOnMenuItemClickListener {
            if (SharedPrefKeyManager.get<Boolean>(Constants.IS_SHIFT) == true)
                navController.navigate(R.id.nav_notification)
            else
                showErrorMessage(getString(R.string.start_your_shift))
            true
        }


        item.setActionView(R.layout.switch_layout)
        switchAB = item.actionView.findViewById(R.id.switchAB)
        sharedPreferences = this.getSharedPreferences("SharedPrefs", MODE_PRIVATE)

        if (SharedPrefKeyManager.get<Boolean>(Constants.IS_SHIFT) == true) {
            switchAB.isChecked = true
        }

        switchAB.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){

                Log.i("xxChecked", "check")
            }else{
                Log.i("xxChecked", "uncheck")
             //  navigateToFragment(R.id.action_nav_home_to_welcome_fragment)
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

            Constants.CALL_LOG -> {
                navigateToFragment(R.id.action_nav_home_to_call_logs)
                closeDrawer()
            }

            Constants.VISIT_LOG -> {
                navigateToFragment(R.id.action_nav_home_to_visit_logs)
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
        listDataHeader.add(Constants.CHANGE_PASSWORD) //10
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

        listDataChild[listDataHeader[3]] = salesManagement


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

            R.id.sync -> {}
            R.id.upload -> {}
            R.id.cold_calling -> {}
//            R.id.addLead -> {
//                navigateToFragment(R.id.nav_visit)
//            }
//            R.id.followup -> {
//                navigateToFragment(R.id.followup_fragment)
//            }
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

    private var x1 = 0f
    private var x2 = 0f
    val MIN_DISTANCE = 60

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

    override fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    override fun navigateToFragment(@IdRes id: Int, args: Bundle?) {
        if (args != null) {
            navController.navigate(id, args)
            navWelcome.navigate(id, args)
            return
        }
        navController.navigate(id)
        navWelcome.navigate(id)
    }
}