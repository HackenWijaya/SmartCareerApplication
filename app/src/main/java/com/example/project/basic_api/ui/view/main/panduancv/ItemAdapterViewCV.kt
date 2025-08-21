package com.example.project.basic_api.ui.view.main.panduancv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R


class ItemAdapterViewCV(private val items: List<ItemModelCV>, private val onItemClick: (ItemModelCV) -> Unit) : RecyclerView.Adapter<ItemAdapterViewCV.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_card_viewpembuatancv, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ItemModelCV) {
            // Bind your data to the views here, for example:
            itemView.findViewById<TextView>(R.id.title).text = item.name
            itemView.findViewById<TextView>(R.id.deskripsi).text = item.desc

            // Set click listener
            itemView.setOnClickListener {
                onItemClick(item)  // Trigger the click callback with the clicked item
            }
        }
    }
}
