package com.example.abl.fragment.portfolio

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.adapter.CustomerAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.LtdFragmentBinding
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Customers(
    var customer_name: String,
    var contact_number: String,
    var account_num: String,
    var lead_status: String
):Parcelable

class LTDFragment : BaseDockFragment(), ClickListner {


    lateinit var binding: LtdFragmentBinding
    val dataList = ArrayList<Customers>()
    lateinit var adapter: CustomerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        initView()

        dummyData(binding.ltd)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pullToRefresh.setOnRefreshListener {
            dummyData(binding.ltd)
        }

        binding.search.setOnClickListener {
            showCustomerDialog()
        }

        binding.filter.setOnClickListener {
            showHideFilter()
        }
    }

    private fun initView() {
        binding = LtdFragmentBinding.inflate(layoutInflater)
    }

    private fun dummyData(ltd: RecyclerView){

        dataList.clear()
        val nameOne = "Muhammad Abdullah"
        val numberOne = "0321-123-4567"
        val accountOne = "XXXXXXXX6789"
        val leadStatusOne = "1"

        val nameTwo = "Muhammad Ali"
        val numberTwo = "0312-123-4567"
        val accountTwo = "XXXXXXXX1234"
        val leadStatusTwo = "1"

        val objectOne = Customers(nameOne, numberOne, accountOne, leadStatusOne)
        val objectTwo = Customers(nameTwo, numberTwo, accountTwo, leadStatusTwo)
        dataList.add(objectOne)
        dataList.add(objectTwo)

        if (dataList.isEmpty())
        {
            Log.i("list", "null")
        }
        else
        {
            adapter = CustomerAdapter(requireContext(), this)
//            adapter.setList(dataList)
//            adapter.notifyDataSetChanged()
//            ltd.adapter = adapter
            binding.pullToRefresh.isRefreshing = false

        }

    }

    private fun showHideFilter(){
        if(binding.filterLayout.root.visibility == View.VISIBLE){
            binding.filterLayout.root.visibility = View.GONE
        }else {
            binding.filterLayout.root.visibility = View.VISIBLE
        }
    }

        override fun <T> onClick(data: T, createNested: Boolean) {
            val bundle = Bundle()
            bundle.putParcelable(Constants.CUSTOMER_DATA, data as Parcelable)
            navigateToFragment(R.id.customerDetailsFragment, bundle)
    }

    private fun showCustomerDialog(){
        val searchCustomerDialogFragment = SearchCustomerDialogFragment()
        searchCustomerDialogFragment.show(childFragmentManager, "visits")
    }
}