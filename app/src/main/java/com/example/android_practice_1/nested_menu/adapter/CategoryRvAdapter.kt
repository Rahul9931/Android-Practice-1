package com.example.android_practice_1.nested_menu.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_practice_1.R
import com.example.android_practice_1.constants.ApplicationConstants
import com.example.android_practice_1.databinding.ItemCategoryBinding
import com.example.android_practice_1.nested_menu.model.Category
import com.google.gson.Gson

class CategoryRvAdapter(
    private val context: Context,
    private val categories: ArrayList<Category>,
    private val level: Int = 0
) : RecyclerView.Adapter<CategoryRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int = categories.size

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.apply {
                data = category
                hasChildren = category.hasChildren
                this.level = level

                // Clear any existing subcategory views (only remove if it's the subcategory RecyclerView)
                (binding.root as? ViewGroup)?.let { rootView ->
                    if (rootView.childCount > 1 && rootView.getChildAt(1) is RecyclerView) {
                        rootView.removeViewAt(1)
                    }
                }

                // Set background and text colors based on level
                val bgColor = when (level) {
                    0 -> ContextCompat.getColor(context, R.color.parent_category_bg)
                    else -> ContextCompat.getColor(context, R.color.child_category_bg_1)
                }
                itemCatLayout.setBackgroundColor(bgColor)

                val textColor = when (level) {
                    0 -> ContextCompat.getColor(context, R.color.parent_category_text)
                    else -> ContextCompat.getColor(context, R.color.child_category_text)
                }
                navDrawerCategoryLabel.setTextColor(textColor)

                // Set arrow visibility and rotation
                arrow.visibility = if (category.hasChildren) View.VISIBLE else View.GONE
                arrow.rotation = if (category.isExpanded) 180f else 0f

                // Set left padding for indentation
                val paddingStart = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    (16 + 24 * (level ?: 0)).toFloat(), // Base 16dp + 24dp per level
                    context.resources.displayMetrics
                ).toInt()
                itemCatLayout.setPadding(
                    paddingStart,
                    itemCatLayout.paddingTop,
                    itemCatLayout.paddingEnd,
                    itemCatLayout.paddingBottom
                )

                itemCatLayout.setOnClickListener {
                    if (category.hasChildren) {
                        category.isExpanded = !category.isExpanded
                        notifyItemChanged(adapterPosition)
                    } else {
                        Toast.makeText(context, "Go to catalog", Toast.LENGTH_SHORT).show()
                    }
                }

                // Add subcategories if expanded
                if (category.isExpanded && category.hasChildren) {
                    addSubcategories(category)
                }
            }
        }

        private fun addSubcategories(category: Category) {
            val subcategoryRecycler = RecyclerView(context).apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CategoryRvAdapter(context, category.child, level + 1)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

            (binding.root as ViewGroup).addView(subcategoryRecycler)
        }
    }
}