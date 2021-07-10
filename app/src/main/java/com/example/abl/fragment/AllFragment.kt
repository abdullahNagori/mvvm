package com.example.abl.fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.adapter.CustomerAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.FragmentAllBinding
import com.example.abl.model.DynamicLeadsItem
import com.example.abl.model.DynamicLeadsResponse
import com.example.abl.utils.GsonFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_all.*
import java.io.Serializable
import java.lang.reflect.Type
import java.util.ArrayList

class AllFragment : BaseDockFragment(), ClickListner {

    lateinit var binding: FragmentAllBinding

    private lateinit var adapter: CustomerAdapter
    private var section: DynamicLeadsResponse? = null

    companion object {
        const val ARG_NAME = "section_data"

        fun newInstance(content: String): AllFragment {
            val fragment = AllFragment()
            val args = Bundle()
            args.putString(ARG_NAME, content)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllBinding.inflate(layoutInflater)
        section = GsonFactory.getConfiguredGson()?.fromJson(arguments?.getString(ARG_NAME), DynamicLeadsResponse::class.java)
        initRecyclerView()
        return binding.root
    }

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        if (args != null) {
            MainActivity.navController.navigate(id, args)
            return
        }
        MainActivity.navController.navigate(id)
    }

    override fun setTitle(text: String) {
        TODO("Not yet implemented")
    }

    override fun <T> initiateListArrayAdapter(list: List<T>): ArrayAdapter<T> {
        TODO("Not yet implemented")
    }

    private fun initRecyclerView(){
        adapter = CustomerAdapter(requireContext(), this)
        section?.let {
            adapter.setList(it.data)
            adapter.notifyDataSetChanged()
            binding.customers.adapter = adapter
        }
    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.LEAD_DATA,data as Parcelable)
        navigateToFragment(R.id.customerDetailsFragment,bundle)
    }



}