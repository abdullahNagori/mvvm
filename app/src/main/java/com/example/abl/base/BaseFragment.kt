package com.example.abl.base

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.abl.activity.DockActivity
import com.example.abl.activity.MainActivity
import com.example.abl.progress.ProgressIndicator
import dagger.android.DaggerActivity

open class BaseFragment : Fragment(), BaseView, ProgressIndicator {


    override fun closeDrawer() {
        if (activity != null) (activity as BaseActivity).closeDrawer()
    }


    override fun showBanner(text: String, type: String) {
        if (activity != null) (activity as BaseActivity).showBanner(text, type)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun blockLeftDrawer(){
        MainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        if (activity != null) (activity as BaseActivity).navigateToFragment(id,args)
    }


    override fun setTitle(text: String) {
        if (activity != null) (activity as BaseActivity).setTitle(text)
    }

    override fun <T> initiateListArrayAdapter(list: List<T>): ArrayAdapter<T> {
        return if (activity != null) (activity as BaseActivity).initiateListArrayAdapter(list)
        else (activity as BaseActivity).initiateListArrayAdapter(list)
    }

    override fun showProgressIndicator() {
        if (activity != null) (activity as BaseActivity).showProgressIndicator()
    }

    override fun hideProgressIndicator() {
        if (activity != null) (activity as BaseActivity).hideProgressIndicator()
    }


}