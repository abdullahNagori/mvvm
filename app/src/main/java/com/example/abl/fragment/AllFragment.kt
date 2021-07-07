package com.example.abl.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import com.example.abl.R
import com.example.abl.adapter.CustomerAdapter
import com.example.abl.base.BaseDockFragment
import com.example.abl.base.ClickListner
import com.example.abl.constant.Constants
import com.example.abl.databinding.FragmentAllBinding
import com.example.abl.model.DynamicLeadsItem
import com.example.abl.model.DynamicLeadsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_all.*
import java.lang.reflect.Type
import java.util.ArrayList

class AllFragment : BaseDockFragment(), ClickListner {

    lateinit var customer: List<DynamicLeadsItem>
    lateinit var binding: FragmentAllBinding
    private lateinit var adapter: CustomerAdapter



    companion object {
        fun newInstance() = AllFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myDockActivity?.getUserViewModel()?.apiListener = this
        binding = FragmentAllBinding.inflate(layoutInflater)
        getDynamicData("")

        return binding.root
    }

    override fun closeDrawer() {
        TODO("Not yet implemented")
    }

    override fun navigateToFragment(id: Int, args: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun setTitle(text: String) {
        TODO("Not yet implemented")
    }

    override fun <T> initiateListArrayAdapter(list: List<T>): ArrayAdapter<T> {
        TODO("Not yet implemented")
    }

    private fun getDynamicData(token: String){
        myDockActivity?.getUserViewModel()?.getDynamicLeads(token)
    }

    override fun onSuccess(liveData: LiveData<String>, tag: String) {
        super.onSuccess(liveData, tag)
        when (tag) {
            Constants.GET_DYNAMIC_LEADS -> {
                try {
                    Log.d("liveDataValue", liveData.value.toString())
                    val gson = Gson()
                    val listType: Type = object : TypeToken<List<DynamicLeadsResponse?>?>() {}.type
                    val posts: List<DynamicLeadsResponse> =
                        gson.fromJson<List<DynamicLeadsResponse>>(liveData.value, listType)

                    initRecyclerView(posts)


                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }
        }
    }

    private fun initRecyclerView(data: List<DynamicLeadsResponse>){
        adapter = CustomerAdapter(requireContext(), this)
        data.forEachIndexed { index, element ->

            Log.i("xx1", element.data.toString())
//            customer = element.data
//            adapter.setList(customer)
//            adapter.notifyDataSetChanged()
//            binding.customers.adapter = adapter
        }
        Log.i("xx2", data[0].data.toString())


            customer = data[0].data
            adapter.setList(customer)
            adapter.notifyDataSetChanged()
            customers.adapter = adapter

    }

    override fun <T> onClick(data: T, createNested: Boolean) {

        Log.i("xx2", "Error")

    }

}