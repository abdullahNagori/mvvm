package com.uhfsolutions.abl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.uhfsolutions.abl.R
import com.uhfsolutions.abl.base.ClickListner
import com.uhfsolutions.abl.databinding.ItemProductListAdapterBinding
import com.uhfsolutions.abl.model.lov.CompanyProduct

class ProductListAdapter (val context: Context?, val listner: ClickListner) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private lateinit var productList : ArrayList<CompanyProduct>
    lateinit var view : ItemProductListAdapterBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(item: CompanyProduct) {
            view.name.text = item.product_name
        }

    }


    fun setList(list: ArrayList<CompanyProduct>){
        productList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = ItemProductListAdapterBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item  = productList[position]
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
            ::productList.isInitialized -> productList.size
            else -> 0
        }
    }
}