package com.gallardf.melisearch.ui.product_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gallardf.melisearch.R
import com.gallardf.melisearch.utils.parseUrl

class CarrouselAdapter(
    private val context: Context,
    private var list:List<String>
) : RecyclerView.Adapter<CarrouselViewHolder>(){

    fun updateList(newList: List<String>){
        val productDiffUtil = CarrouselDiffCallback(list,newList)
        val result = DiffUtil.calculateDiff(productDiffUtil)

        list = newList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrouselViewHolder {
        return CarrouselViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_image_preview,parent,false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CarrouselViewHolder, position: Int) {
        holder.render(context,list[position])
    }
}

class CarrouselViewHolder(view:View):RecyclerView.ViewHolder(view){

    private val carrouselImage = view.findViewById<AppCompatImageView>(R.id.view_item_image_preview_image)

    fun render(context: Context, imageUrl: String){

        Glide.with(context)
            .load(imageUrl)
            .error(R.drawable.ic_image_unsupported_32dp)
            .into(carrouselImage)
    }
}

class CarrouselDiffCallback(
    private val oldList:List<String>,
    private val newList: List<String>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}