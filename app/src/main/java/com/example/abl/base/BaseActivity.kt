package com.example.abl.base

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.constant.Constants
import com.example.abl.progress.ProgressDialog
import com.example.abl.progress.ProgressIndicator
import kotlinx.android.synthetic.main.activity_main.*


open class BaseActivity : AppCompatActivity(), ProgressIndicator, BaseView {

    private lateinit var progressBarDialog: ProgressDialog

    override fun showProgressIndicator() {
        progressBarDialog = ProgressDialog()
        progressBarDialog.showDialog(
            supportFragmentManager,
            BaseActivity::class.java.simpleName
        )

    }

    override fun hideProgressIndicator() {
        if (this::progressBarDialog.isInitialized && progressBarDialog.isAdded ) {
            progressBarDialog.dismiss()
        }
    }

    override fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    override fun showBanner(text: String, type: String) {

        if (findViewById<View?>(R.id.banner) != null) {

            findViewById<View>(R.id.banner).findViewById<TextView>(R.id.txtMsg).text = text
            findViewById<View>(R.id.banner).startAnimation(
                AnimationUtils.loadAnimation(
                    applicationContext,
                    R.anim.banner_in
                )
            )

            findViewById<View>(R.id.banner).findViewById<ImageView>(R.id.imgBannerLogo)
                .startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.blink_anim))


            // for error case
            if (type == Constants.ERROR) {
                findViewById<View>(R.id.banner).setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.red
                    )
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.red)
                }
            } else {
                findViewById<View>(R.id.banner).setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.banner_green_color
                    )
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor =
                        ContextCompat.getColor(applicationContext, R.color.banner_green_color)
                }
            }

            findViewById<View>(R.id.banner).visibility = View.VISIBLE
            findViewById<View>(R.id.banner).bringToFront()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            findViewById<View>(R.id.banner).startAnimation(
                AnimationUtils.loadAnimation(
                    applicationContext,
                    R.anim.banner_out
                )
            )
            findViewById<View>(R.id.banner).visibility = View.INVISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = ContextCompat.getColor(
                        applicationContext,
                        R.color.colorPrimaryDark
                    )
                }
            }, 650)
        }, 2000)
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        if (args != null) {
            MainActivity.navController.navigate(id, args)
            return
        }
        MainActivity.navController.navigate(id)
    }

    override fun setTitle(text: String) {
        supportActionBar?.title = text
    }

//    override fun hideKeyboard() {
//        hideKeyboard(this)
//    }

    override fun <T> initiateListArrayAdapter(list: List<T>): ArrayAdapter<T> {
        val adapter = ArrayAdapter(this, R.layout.item_spinner, list)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        return adapter
    }

}