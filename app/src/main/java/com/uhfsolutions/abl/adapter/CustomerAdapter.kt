package com.uhfsolutions.abl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.base.ClickListner
import com.uhfsolutions.abl.databinding.ItemCustomerBinding
import com.uhfsolutions.abl.model.addLead.DynamicLeadsItem

class CustomerAdapter(val context: Context?, val listner: ClickListner) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {

    private lateinit var customerList : List<DynamicLeadsItem>
    lateinit var view : ItemCustomerBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(item: DynamicLeadsItem) {
            view.customerName.text = item.first_name + " " + item.last_name
            view.accountNumber.text = "Account Number: -"
            view.phoneNumber.text = "Phone Number: " + item.mobile_phone_number
            view.leadStatus.text = "Lead Status: " + (item.lead_status_name ?: "-")
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