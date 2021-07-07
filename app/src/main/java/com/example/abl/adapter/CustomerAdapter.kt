package com.example.abl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.base.ClickListner
import com.example.abl.databinding.ItemCustomerBinding
import com.example.abl.fragment.Customers
import com.example.abl.model.DynamicLeadsItem

class CustomerAdapter(val context: Context?, val listner: ClickListner) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    private lateinit var customerList : List<DynamicLeadsItem>
    lateinit var view : ItemCustomerBinding



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){



        fun bindItems(item: DynamicLeadsItem) {
            view.customerName.text = (item.first_name ?: "N/A")
            view.phoneNumber.text = "Phone Number : "+item.mobile_phone_number
            view.accountNumber.text = "Account Number : "+ (item.mobile_phone_number ?: "N/A")
            //view.branch.text = "Branch : "+(item.branch ?: "N/A")
            view.leadStatus.text = "Lead Status : "+(item.lead_status ?: "N/A")
        }

    }



    fun setList(list: List<DynamicLeadsItem>){
        customerList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = ItemCustomerBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item  = customerList[position]
        holder.bindItems(item)

        holder.itemView.setOnClickListener {
            listner.onClick(item)
        }

        holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.slide_transition_animation)
        holder.setIsRecyclable(false)
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() : Int {
        return when {
            ::customerList.isInitialized -> customerList.size
            else -> 0
        }
    }
}