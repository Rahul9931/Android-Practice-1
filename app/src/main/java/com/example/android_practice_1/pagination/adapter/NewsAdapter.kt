package com.example.android_practice_1.pagination.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_practice_1.databinding.ActivityRetrofitPracticeBinding
import com.example.android_practice_1.databinding.CustomModelBinding
import com.example.android_practice_1.mvvm.model.Article

class NewsAdapter(private val articles : MutableList<Article>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){
    class NewsViewHolder(val binding: CustomModelBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = CustomModelBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.txtNum.text = articles[position].title
    }

    fun addArticles(newArticles : MutableList<Article>){
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }
}