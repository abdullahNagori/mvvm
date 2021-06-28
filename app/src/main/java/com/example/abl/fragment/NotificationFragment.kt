package com.example.abl.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abl.R
import com.example.abl.databinding.NotificationFragmentBinding

class NotificationFragment : Fragment() {

    lateinit var binding: NotificationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.notification_fragment, container, false)
    }

    private fun initView(){

        binding = NotificationFragmentBinding.inflate(layoutInflater)
    }
}