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
}