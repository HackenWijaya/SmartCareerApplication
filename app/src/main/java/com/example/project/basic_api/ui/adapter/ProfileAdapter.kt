package com.example.project.basic_api.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.basic_api.data.model.LamaranModel
import com.example.project.databinding.ItemProfilBinding

class ProfileAdapter(private var profilList: List<LamaranModel>) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemProfilBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProfilBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profil = profilList[position]
        with(holder.binding) {
            name.text = profil.name
            // Jika status kosong atau null, tampilkan "Masih di Proses"
            status.text = if (profil.status.isNullOrEmpty()) "Masih di Proses" else profil.status
        }
    }

    override fun getItemCount(): Int = profilList.size

    fun updateData(newData: List<LamaranModel>) {
        profilList = newData
        notifyDataSetChanged()
    }
}