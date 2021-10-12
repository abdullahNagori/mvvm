package com.uhfsolutions.abl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.base.ClickListner
import com.uhfsolutions.abl.databinding.ItemPreviousBinding
import com.uhfsolutions.abl.model.previousVisits.GetPreviousVisit

class PreviousVisitAdapter(val context: Context?, val onclick: ClickListner) : RecyclerView.Adapter<PreviousVisitAdapter.ViewHolder>() {

    lateinit var previousList : List<GetPreviousVisit>
    lateinit var view: ItemPreviousBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: GetPreviousVisit) {
            view.type.text = (item.visit_type ?: "N/A")
            view.date.text = (item.visited_time ?: "N/A")
            view.status.text =  (item.lead_status_name ?: "N/A")
          //  view.visitedBy.text = (item.visit_by ?: "N/A")
        }
    }


    fun setList(list: List<GetPreviousVisit>) {
        previousList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = ItemPreviousBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(previousList[position])
        holder.itemView.setOnClickListener {
            onclick.onClick(previousList[position])
        }
        holder.itemView.animation =
            AnimationUtils.loadAnimation(context, R.anim.slide_transition_animation)
    }

    override fun getItemCount(): Int {
        return if (::previousList.isInitialized) previousList.size else 0
    }
}