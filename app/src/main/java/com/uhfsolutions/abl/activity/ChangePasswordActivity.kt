package com.uhfsolutions.abl.activity

import android.os.Bundle
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.databinding.ActivityChangePasswordBinding
import com.uhfsolutions.abl.fragment.login.ChangePasswordFragment

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