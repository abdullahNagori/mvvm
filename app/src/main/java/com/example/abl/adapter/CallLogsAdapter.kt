package com.example.abl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.base.ClickListner
import com.example.abl.databinding.ItemCallLogsBinding
import com.example.abl.model.checkin.CheckinModel

class CallLogsAdapter(val context: Context?, val onclick: ClickListner) : RecyclerView.Adapter<CallLogsAdapter.ViewHolder>() {

    lateinit var callList : List<CheckinModel>
    lateinit var view: ItemCallLogsBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: CheckinModel) {
            view.time.text = (item.visit_date_time ?: "N/A")
            view.mobileNumber.text = (item.mobile_phone_number ?: "N/A")
            view.customerName.text =  (item.customer_name ?: "N/A")
            view.status.text = "Status: ${item.visit_status}"
        }
    }


    fun setList(list: List<CheckinModel>) {
        callList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = ItemCallLogsBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(callList[position])
        holder.itemView.setOnClickListener {
            onclick.onClick(callList[position])
        }
        holder.itemView.animation =
            AnimationUtils.loadAnimation(context, R.anim.slide_transition_animation)
    }

    override fun getItemCount(): Int {
        return if (::callList.isInitialized) callList.size else 0
    }
}