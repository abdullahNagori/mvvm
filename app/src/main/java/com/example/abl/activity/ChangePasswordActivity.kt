package com.example.abl.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.abl.R
import com.example.abl.databinding.ActivityChangePasswordBinding
import com.example.abl.fragment.ChangePasswordFragment
import com.example.abl.fragment.WelcomeFragment
import com.example.abl.model.DynamicLeadsItem
import com.tapadoo.alerter.Alerter

class ChangePasswordActivity : DockActivity() {

    lateinit var binding: ActivityChangePasswordBinding
    override fun getDockFrameLayoutId(): Int {
        return R.id.container
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment()
    }

    private fun initFragment() {
        replaceDockableFragmentWithoutBackStack(ChangePasswordFragment())
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

    override fun onFailureWithResponseCode(code: Int, message: String, tag: String) {
        TODO("Not yet implemented")
    }
}