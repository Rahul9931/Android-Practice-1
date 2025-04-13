package com.example.android_practice_1.search_pagination.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_practice_1.R
import com.example.android_practice_1.search_pagination.model.Product

class ProductAdapter(
    private var products: List<Product>,
    private val onEditClick: (Product) -> Unit,
    private val onDeleteClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.productTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.productDescription)
        private val priceTextView: TextView = itemView.findViewById(R.id.productPrice)
        private val thumbnailImageView: ImageView = itemView.findViewById(R.id.productThumbnail)
        private val editButton: Button = itemView.findViewById(R.id.editButton)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(product: Product) {
            titleTextView.text = product.title
            descriptionTextView.text = product.description
            priceTextView.text = "$${product.price}"

            Glide.with(itemView.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(thumbnailImageView)

            editButton.setOnClickListener { onEditClick(product) }
            deleteButton.setOnClickListener { onDeleteClick(product) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun addProducts(newProducts: List<Product>) {
        val currentSize = products.size
        products = products + newProducts
        notifyItemRangeInserted(currentSize, newProducts.size)
    }

    fun removeProduct(product: Product) {
        val position = products.indexOfFirst { it.id == product.id }
        if (position != -1) {
            products = products.toMutableList().apply { removeAt(position) }
            notifyItemRemoved(position)
        }
    }
}