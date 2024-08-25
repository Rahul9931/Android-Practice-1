package com.example.android_practice_1

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_practice_1.databinding.ActivityUiCheckBinding
import com.example.android_practice_1.databinding.CustomModelBinding

class CustomAdapter(val context: Context, val list: MutableList<Int>):RecyclerView.Adapter<CustomAdapter.CustomAdapterViewHolder>() {
    class CustomAdapterViewHolder(val binding:CustomModelBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomAdapter.CustomAdapterViewHolder {
        val binding = CustomModelBinding.inflate(LayoutInflater.from(context),parent,false)
        return CustomAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomAdapter.CustomAdapterViewHolder, position: Int) {
        holder.binding.txtNum.text = list[position].toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}