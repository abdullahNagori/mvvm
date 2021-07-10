package com.example.abl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.databinding.ItemCheckboxBinding
import com.example.abl.model.CompanyVisitStatu
import kotlinx.android.synthetic.main.item_checkbox.view.*

class SpinnerStatusAdapter(val context: Context?, val list: List<CompanyVisitStatu>, val selectedList: ArrayList<CompanyVisitStatu>) : RecyclerView.Adapter<SpinnerStatusAdapter.ViewHolder>() {

    lateinit var view: ItemCheckboxBinding
    val newProductList : ArrayList<CompanyVisitStatu> = ArrayList<CompanyVisitStatu>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val checkbox : CheckBox = itemView.checkbox

        fun bindItems(item: CompanyVisitStatu) {
            view.text.text = item.name.toString()

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpinnerStatusAdapter.ViewHolder {
        view = ItemCheckboxBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])


        if (selectedList.isNotEmpty()) {
            for (item in selectedList){
                if(item.name==list[position].name) {
                    view.checkbox.isChecked=true
                }
            }
        }
        holder.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                newProductList.add((list[position]))
            }else {
                newProductList.remove(list[position])
                selectedList.remove(list[position])
            }
        }


        holder.itemView.animation =
            AnimationUtils.loadAnimation(context, R.anim.slide_transition_animation)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
