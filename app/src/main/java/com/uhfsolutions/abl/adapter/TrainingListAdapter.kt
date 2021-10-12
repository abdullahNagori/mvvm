package com.uhfsolutions.abl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.base.ClickListner
import com.uhfsolutions.abl.databinding.ItemTrainingListAdapterBinding
import com.uhfsolutions.abl.model.trainingAndQuiz.Training

class TrainingListAdapter (val context: Context?, val onclick: ClickListner) : RecyclerView.Adapter<TrainingListAdapter.ViewHolder>() {

    lateinit var callList : List<Training>
    lateinit var view: ItemTrainingListAdapterBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: Training) {
            view.name.text = (item.training_name ?: "N/A")
        }
    }


    fun setList(list: List<Training>) {
        callList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = ItemTrainingListAdapterBinding.inflate(LayoutInflater.from(context), parent, false)
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