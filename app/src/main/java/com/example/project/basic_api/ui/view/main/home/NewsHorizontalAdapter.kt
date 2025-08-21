package com.example.project.basic_api.ui.view.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.databinding.FragmentHomeBinding
import com.example.project.databinding.HomeNewsHorizontalItemBinding
import com.squareup.picasso.Picasso

class NewsHorizontalAdapter(

    private var mList: List<NewsHorizontalModel>,
) : RecyclerView.Adapter<NewsHorizontalAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeNewsHorizontalItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]

        holder.binding.newsHoriTitle.text = item.newsTitle
        Picasso.get().load(item.imageUrl).into(holder.binding.newsHoriImage)
        holder.itemView.setOnClickListener {
            Log.i("RecyclerView", "Anda klik item ke $position : ${item.newsTitle}")
        }
    }

    fun updateDataSet(newsItems: List<NewsHorizontalModel>){
        mList = newsItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding: HomeNewsHorizontalItemBinding) : RecyclerView.ViewHolder(binding.root)
}