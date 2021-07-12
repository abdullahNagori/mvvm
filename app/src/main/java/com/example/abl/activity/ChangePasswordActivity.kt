package com.example.abl.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.abl.R
import com.example.abl.databinding.ActivityChangePasswordBinding
import com.example.abl.fragment.ChangePasswordFragment
import com.example.abl.fragment.WelcomeFragment
import com.example.abl.model.DynamicLeadsItem

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
        TODO("Not yet implemented")
    }

    override fun showSuccessMessage(message: String) {
        TODO("Not yet implemented")
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
}