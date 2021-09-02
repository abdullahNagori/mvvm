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
import com.example.abl.model.CheckinModel

class VisitsLogsAdapter (val context: Context?, val onclick: ClickListner) : RecyclerView.Adapter<VisitsLogsAdapter.ViewHolder>() {

    lateinit var callList : List<CheckinModel>
    lateinit var view: ItemPreviousBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: CheckinModel) {
            view.type.text = (item.visit_type ?: "N/A")
            view.date.text = (item.visit_date_time ?: "N/A")
            view.status.text =  (item.visit_status ?: "N/A")
            view.visitedBy.text = (item.user_name ?: "N/A")
        }
    }


    fun setList(list: List<CheckinModel>) {
        callList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = ItemPreviousBinding.inflate(LayoutInflater.from(context), parent, false)
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