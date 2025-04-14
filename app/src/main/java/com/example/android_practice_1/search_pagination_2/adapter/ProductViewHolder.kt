package com.example.android_practice_1.search_pagination_2.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_practice_1.R
import com.example.android_practice_1.search_pagination_2.model.Product

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val productName: TextView = itemView.findViewById(R.id.product_name)

    fun bind(product: Product) {
        productName.text = product.name
    }
}