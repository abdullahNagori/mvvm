package com.example.abl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.base.ClickListner
import com.example.abl.databinding.ItemUserTrackingAdapterBinding
import com.example.abl.model.location.UserLocation

class UserTrackingAdapter(val context: Context?, val onclick: ClickListner) : RecyclerView.Adapter<UserTrackingAdapter.ViewHolder>() {

    lateinit var previousList : List<UserLocation>
    lateinit var view: ItemUserTrackingAdapterBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: UserLocation) {
            view.latitude.text = item.latitude.toString()
            view.longitude.text = item.longitude.toString()
            //  view.visitedBy.text = (item.visit_by ?: "N/A")
        }
    }


    fun setList(list: List<UserLocation>) {
        previousList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = ItemUserTrackingAdapterBinding.inflate(LayoutInflater.from(context), parent, false)
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