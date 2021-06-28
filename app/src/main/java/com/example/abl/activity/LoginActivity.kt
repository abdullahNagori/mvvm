package com.example.abl.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.abl.R
import com.example.abl.base.BaseActivity
import com.example.abl.databinding.ActivityLoginBinding
import com.tapadoo.alerter.Alerter

class LoginActivity : DockActivity() {

    lateinit var binding: ActivityLoginBinding

    companion object {
        lateinit var navController : NavController
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_login_fragment)
    }

    override fun getDockFrameLayoutId(): Int {
        return R.id.container
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

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.nav_host_login_fragment)
        return super.onSupportNavigateUp()
    }

    override fun onLoadingStarted() {
//        progressBar?.visibility = View.VISIBLE
//        loading = true
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onLoadingFinished() {
        TODO("Not yet implemented")
    }
}