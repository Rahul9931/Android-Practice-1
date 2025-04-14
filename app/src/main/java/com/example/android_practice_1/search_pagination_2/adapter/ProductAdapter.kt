package com.example.android_practice_1.search_pagination_2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_practice_1.R
import com.example.android_practice_1.databinding.ItemProductBinding
import com.example.android_practice_1.search_pagination_2.model.Product
import com.example.android_practice_1.search_pagination_2.model.Ribbon

class ProductAdapter(private val products: MutableList<Product>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ProductViewHolder(binding)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ProductViewHolder) {
            holder.bind(products[position])
        }
    }

    override fun getItemCount(): Int = products.size

    override fun getItemViewType(position: Int): Int {
        return if (products[position].templateId == -1) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun addProducts(newProducts: List<Product>) {
        val startPosition = products.size
        products.addAll(newProducts)
        notifyItemRangeInserted(startPosition, newProducts.size)
    }

    fun clearProducts() {
        products.clear()
        notifyDataSetChanged()
    }

    fun addLoadingFooter() {
        products.add(Product(
            templateId = -1,
            name = "",
            priceUnit = "",
            priceReduce = "",
            productId = -1,
            productCount = 0,
            description = "",
            thumbNail = "",
            ribbon = Ribbon("", "", "", "")
        ))
        notifyItemInserted(products.size - 1)
    }

    fun removeLoadingFooter() {
        if (products.isNotEmpty() && products.last().templateId == -1) {
            products.removeAt(products.size - 1)
            notifyItemRemoved(products.size)
        }
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productName.text = product.name
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}