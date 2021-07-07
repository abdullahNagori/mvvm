package com.example.abl.fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.adapter.CustomerAdapter
import com.example.abl.base.BaseFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.FollowupFragmentBinding

class FollowupFragment : BaseFragment(), ClickListner {

    lateinit var binding: FollowupFragmentBinding
    val dataList = ArrayList<Customers>()
    lateinit var adapter: CustomerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    }

    private fun initView(){
        binding = FollowupFragmentBinding.inflate(layoutInflater)
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
//            binding.pullToRefresh.isRefreshing = false

        }

    }

    override fun <T> onClick(data: T, createNested: Boolean) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.CUSTOMER_DATA, data as Parcelable)
        navigateToFragment(R.id.customerDetailsFragment, bundle)
    }
}