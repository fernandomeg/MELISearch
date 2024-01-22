package com.gallardf.melisearch.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gallardf.melisearch.R
import com.gallardf.melisearch.domain.models.ProductModel
import com.gallardf.melisearch.utils.formatToCurrency
import com.gallardf.melisearch.utils.parseUrl

class ProductsAdapter(
    private val context: Context,
    private var list:List<ProductModel>,
    private val onItemSelected: (product:ProductModel) -> Unit
) : RecyclerView.Adapter<ProductsViewHolder>(){

    fun updateList(newList: List<ProductModel>){
        val productDiffUtil = ProductsDiffCallback(list,newList)
        val result = DiffUtil.calculateDiff(productDiffUtil)

        list = newList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_card_product_item,parent,false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.render(context,list[position],onItemSelected)
    }
}

class ProductsViewHolder(view:View):RecyclerView.ViewHolder(view){

    private val container = view.findViewById<CardView>(R.id.view_card_product_item_container)
    private val productImage = view.findViewById<ImageView>(R.id.view_card_product_item_image)
    private val productName = view.findViewById<TextView>(R.id.view_card_product_item_title)
    private val productPrice = view.findViewById<TextView>(R.id.view_card_product_item_price)

    fun render(context: Context, product: ProductModel, onItemSelected: (product: ProductModel) -> Unit){

        container.setOnClickListener {
            onItemSelected(product)
        }

        Glide.with(context)
            .load(product.imageUrl?.parseUrl())
            .error(R.drawable.ic_image_unsupported_32dp)
            .into(productImage)
        productName.text = product.title
        productPrice.text = product.price?.formatToCurrency() ?: "$0.00"

    }
}

class ProductsDiffCallback(
    private val oldList:List<ProductModel>,
    private val newList: List<ProductModel>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}