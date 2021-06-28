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
import com.example.abl.databinding.ItemPreviousBinding
import com.example.abl.fragment.CallLogsList

class CallLogsAdapter(val context: Context?, val onclick: ClickListner) : RecyclerView.Adapter<CallLogsAdapter.ViewHolder>() {

    lateinit var callList : ArrayList<CallLogsList>
    lateinit var view: ItemCallLogsBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: CallLogsList) {
            view.time.text = (item.time ?: "N/A")
            view.mobileNumber.text = (item.mobile ?: "N/A")
            view.customerName.text =  (item.name ?: "N/A")
            view.status.text = (item.status ?: "N/A")
        }
    }


    fun setList(list: ArrayList<CallLogsList>) {
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