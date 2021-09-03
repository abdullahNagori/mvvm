package com.example.abl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.base.ClickListner
import com.example.abl.databinding.ItemCheckboxOptionBinding
import com.example.abl.model.Option
import kotlinx.android.synthetic.main.item_checkbox.view.*
import kotlinx.android.synthetic.main.item_checkbox_option.view.*

class QuizOptionsAdapter (val context: Context?, val onclick: ClickListner) : RecyclerView.Adapter<QuizOptionsAdapter.ViewHolder>() {

    lateinit var view: ItemCheckboxOptionBinding
    lateinit var optionsList : List<Option>
    val optionViewList  = ArrayList<View>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

      //  val checkbox : RadioButton = itemView.checkbox

        fun bindItems(item: Option) {
            view.optionOne.text = item.option
            optionViewList.add((itemView))
        }
    }

    fun getAllItems():ArrayList<View>{
        return optionViewList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizOptionsAdapter.ViewHolder {
        view = ItemCheckboxOptionBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view.root)
    }

    fun setList(list: List<Option>) {
        optionsList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(optionsList[position])

        holder.itemView.optionOne.setOnClickListener {
            onclick.onClick(optionsList[position])

            //optionsList.add((list[position]))
        }
        holder.itemView.animation =
            AnimationUtils.loadAnimation(context, R.anim.slide_transition_animation)
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }
}
