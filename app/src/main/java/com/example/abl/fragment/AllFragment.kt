package com.example.abl.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.adapter.CustomerAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.FragmentAllBinding
import com.example.abl.model.*
import com.example.abl.utils.GsonFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_all.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.io.Serializable
import java.lang.reflect.Type
import java.util.ArrayList

class AllFragment : BaseDockFragment(), ClickListner {
    lateinit var binding: FragmentAllBinding
    lateinit var adapter: CustomerAdapter
    var leadData:List<DynamicLeadsItem>? = null
    private var leadStatusData: CompanyLeadStatu? = null
    private var leadSourceData: CompanyLeadSource? = null
    var sourceList = listOf<DynamicLeadsItem>()
    var statusList = listOf<DynamicLeadsItem>()

    companion object {
        //const val ARG_NAME = "section_data"
        fun newInstance(content: String): AllFragment {
            val fragment = AllFragment()
            val args = Bundle()
            args.putString(Constants.ARG_NAME, content)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myDockActivity?.getUserViewModel()?.apiListener = this
        binding = FragmentAllBinding.inflate(layoutInflater)
//        leadStatusData = GsonFactory.getConfiguredGson()?.fromJson(arguments?.getString(Constants.ARG_NAME), CompanyLeadStatu::class.java)

        Log.i("xxLeadSource", arguments?.getString(Constants.SOURCE_REC_ID).toString())
        initRecyclerView()
        CoroutineScope(Dispatchers.Main).launch {
            leadData = roomHelper.getLeadsData()
            sourceList = leadData?.filter { it.source.equals(arguments?.getString(Constants.SOURCE_REC_ID),true) }!!
        }.invokeOnCompletion {
            if(arguments?.getString(Constants.ARG_NAME)!! == "all") {
               setData(sourceList)
             }else{
                statusList = sourceList.filter { it.lead_status_name.equals(arguments?.getString(Constants.ARG_NAME), true) }
                setData(statusList)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        binding.customers.adapter = adapter
    }

    private fun setData(list: List<DynamicLeadsItem>) {

        if (leadData != null) {
//            if (leadStatusData?.name.equals("all", true)) {
//                adapter.setList(leadData!!)
//                adapter.notifyDataSetChanged()
//            } else {
               // val filterData = leadData?.filter { it.lead_status_name.equals(leadStatusData?.record_id, true) }
               // val filterData = leadData?.filter { it.lead_status_name.equals(leadSourceData?.record_id, true) }
                adapter.setList(list)
                adapter.notifyDataSetChanged()
            }
        }

    override fun <T> onClick(data: T, createNested: Boolean) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.LEAD_DATA,data as Parcelable)
        navigateToFragment(R.id.customerDetailsFragment,bundle)
    }
}
