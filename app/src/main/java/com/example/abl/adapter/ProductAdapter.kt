package com.example.abl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.databinding.ItemCheckboxBinding
import com.example.abl.model.lov.CompanyProduct
import kotlinx.android.synthetic.main.item_checkbox.view.*

class ProductAdapter(val context: Context?, val list: List<CompanyProduct>, val selectedList: ArrayList<CompanyProduct>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    lateinit var view: ItemCheckboxBinding
    val newProductList : ArrayList<CompanyProduct> = ArrayList<CompanyProduct>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val checkbox : RadioButton = itemView.checkbox

        fun bindItems(item: CompanyProduct) {
            view.text.text = item.product_name.toString()

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        view = ItemCheckboxBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])


        if (selectedList.isNotEmpty()) {
            for (item in selectedList){
                if(item.product_name==list[position].product_name) {
                    view.checkbox.isChecked=true
                }
            }
        }
        holder.checkbox.setOnClickListener {

         //   holder.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
             //   if (isChecked) {
                    newProductList.add((list[position]))
//                } else {
//                    newProductList.remove(list[position])
//                    selectedList.remove(list[position])

           // }

        }
        holder.itemView.animation =
            AnimationUtils.loadAnimation(context, R.anim.slide_transition_animation)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
