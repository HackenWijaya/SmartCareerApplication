//package com.example.project.basic_recycleview
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.project.R
//import com.squareup.picasso.Picasso
//
//class ItemAdapter (
//    private val mlist: List<ItemModel>
//): RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
//        val view = LayoutInflater.from(parent.context).
//        inflate(R.layout.list_card_view, parent, false)
//
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
//        val item = mlist[position]
//        Picasso.get().load(item.imageUrl).into(holder.image)
//        holder.textnama.text = item.description
//
//        println("loading item pada posisi = $position")
//    }
//
//    override fun getItemCount(): Int {
//        return mlist.size
//    }
////    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
////        val image: ImageView = itemView.findViewById(R.id.gambarRecycle)
////        val textnama: TextView = itemView.findViewById(R.id.description)
////    }
//}