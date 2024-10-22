package com.example.android_practice_1.pagination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.paging.PagingDataAdapter
import com.example.android_practice_1.databinding.CustomModel2Binding
import com.example.android_practice_1.mvvm.model.Article


class DataPagingAdapter : PagingDataAdapter<Article, DataPagingAdapter.DataViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    class DataViewHolder(private val binding: CustomModel2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: Article) {
            binding.txtNum.text = dataItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = CustomModel2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}
