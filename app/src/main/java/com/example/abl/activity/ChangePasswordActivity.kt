package com.example.abl.activity

import android.os.Bundle
import com.example.abl.R
import com.example.abl.databinding.ActivityChangePasswordBinding
import com.example.abl.fragment.login.ChangePasswordFragment

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