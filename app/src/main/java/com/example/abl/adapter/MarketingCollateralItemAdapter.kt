package com.example.abl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.abl.R
import com.example.abl.base.ClickListner
import com.example.abl.databinding.ItemMarketingAdapterBinding
import com.example.abl.model.marketingCollateral.MarketingCollateralItem
import kotlinx.android.synthetic.main.item_marketing_adapter.view.*

class MarketingCollateralItemAdapter (val context: Context?, val listner: ClickListner) : RecyclerView.Adapter<MarketingCollateralItemAdapter.ViewHolder>() {

    private lateinit var customerList : List<MarketingCollateralItem>
    lateinit var view : ItemMarketingAdapterBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(item: MarketingCollateralItem) {
            view.title.text = item.title
            view.description.text = item.description
//            view.phoneNumber.text = "Phone Number: " + item.mobile_phone_number
//            view.leadStatus.text = "Lead Status: " + (item.lead_status_name ?: "-")
        }
    }

    fun setList(list: List<MarketingCollateralItem>){
        customerList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = ItemMarketingAdapterBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item  = customerList[position]
        holder.bindItems(item)

        holder.itemView.setOnClickListener {
            listner.onClick(item)
        }

        when {
//            item.file_type.toString().equals("ppt", true) -> {
//                holder.icon.setImageResource(R.drawable.ic_ppt)
//            }
            item.file_type.toString().equals("document", true) -> {
                holder.itemView.icon.setImageResource(R.drawable.ic_doc_icon)
            }
//            item.file_type.toString().equals("video", true) -> {
//                holder.icon.setImageResource(R.drawable.ic_youtube)
//            }
//            item.file_type.toString().equals("image", true) -> {
//                Glide.with(context)
//                    .load(item.link)
//                    .error(R.drawable.ic_broken_image)
//                    .into(holder.icon)
//            }
        }
        holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.slide_transition_animation)
        holder.setIsRecyclable(false)
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() : Int {
        return when {
            ::customerList.isInitialized -> customerList.size
            else -> 0
        }
    }
}