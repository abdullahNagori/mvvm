package com.example.abl.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.abl.R
import com.example.abl.activity.MainActivity
import com.example.abl.adapter.CustomerAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.FragmentAllBinding
import com.example.abl.model.*
import com.example.abl.utils.GsonFactory

class AllFragment : BaseDockFragment(), ClickListner {
    lateinit var binding: FragmentAllBinding
    lateinit var adapter: CustomerAdapter

    var leadStatusName: String? = null
    var leadSource: CompanyLeadSource? = null
    var dataSource:List<DynamicLeadsItem> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myDockActivity?.getUserViewModel()?.apiListener = this
        binding = FragmentAllBinding.inflate(layoutInflater)

        leadStatusName = arguments?.getString(Constants.LEAD_STATUS_NAME)

        val leadSourceJSON = arguments?.getString(Constants.LEAD_SOURCE_DATA)
        leadSource = GsonFactory.getConfiguredGson()?.fromJson(leadSourceJSON, CompanyLeadSource::class.java)

        initRecyclerView()
        setData()


        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setData()
//    }

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

    private fun setData() {
        val leadData = roomHelper.getLeadsData(leadSource?.record_id ?: "0")

        if (leadStatusName.equals("all", true)) {
            this.dataSource = leadData
            adapter.setList(dataSource)
            adapter.notifyDataSetChanged()
        } else {
            this.dataSource = leadData.filter { it.lead_status_name.equals(leadStatusName, true) }
            adapter.setList(dataSource)
            adapter.notifyDataSetChanged()
        }
    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.LEAD_DATA, data as Parcelable)
        navigateToFragment(R.id.customerDetailsFragment,bundle)
    }
}
