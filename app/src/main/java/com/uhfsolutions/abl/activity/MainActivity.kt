package com.uhfsolutions.abl.activity

import android.annotation.SuppressLint

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import butterknife.ButterKnife
import butterknife.Unbinder
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.adapter.ExpandableListAdapter
import com.uhfsolutions.abl.constant.Constants
import com.uhfsolutions.abl.databinding.ActivityMainBinding
import com.uhfsolutions.abl.utils.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : DockActivity(){

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var unbinder: Unbinder
        @SuppressLint("StaticFieldLeak")
        lateinit var navController: NavController
        lateinit var drawerLayout: DrawerLayout
    }

    lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var contentView: ConstraintLayout
    val END_SCALE = 0.7f
    private lateinit var actionBarMenu: Menu
    lateinit var listDataChild: HashMap<String, List<String>>
    lateinit var listDataHeader: ArrayList<String>

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

        initView()
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.nav_host_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        actionBarMenu = menu
        actionBarMenu.findItem(R.id.action_notification).setOnMenuItemClickListener {
            true
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
                R.id.nav_home
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        animateNavigationDrawer(drawerLayout)

        prepareSideMenu()
    }

    private fun animateNavigationDrawer(drawerLayout: DrawerLayout) {

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

        listDataHeader.add(Constants.HOME) //0

        val listAdapter = ExpandableListAdapter(
            this,
            listDataHeader,
            listDataChild,
            icons
        )

        // setting list adapter
        binding.sideLayout.lvExp.setAdapter(listAdapter)


        // Listview parent node click listener
        binding.sideLayout.lvExp.setOnGroupExpandListener { groupPosition: Int ->
            fragmentClickEvent(listDataHeader[groupPosition])
        }
    }


}