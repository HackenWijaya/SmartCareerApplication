package com.example.project.basic_api.ui.view.main.rekomendasipekerjaan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project.basic_api.data.model.KelolaModel
import com.example.project.databinding.ItemKelolaUserBinding
import com.squareup.picasso.Picasso

class KelolaUserAdapter(
    private val onItemClick: (Bundle) -> Unit
) : ListAdapter<KelolaModel, KelolaUserAdapter.KelolaUserViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KelolaUserViewHolder {
        val binding = ItemKelolaUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KelolaUserViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: KelolaUserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class KelolaUserViewHolder(
        private val binding: ItemKelolaUserBinding,
        private val onItemClick: (Bundle) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(kelola: KelolaModel) {
            // Set data ke UI
            binding.textViewTitleUser.text = kelola.judul
            binding.textViewDescriptionUser.text = kelola.deskripsi
            Picasso.get()
                .load(kelola.gambarUrl)
                .error(com.example.project.R.drawable.ic_user)
                .into(binding.imageViewKelolaUser)

            // Set click listener untuk tombol
            binding.buttonCekPekerjaan.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("judul", kelola.judul)
                    putString("deskripsi", kelola.deskripsi)
                    putString("detail", kelola.detail)
                    putString("syarat", kelola.syarat)
                    putString("tanggalMulai", kelola.tanggalMulai)
                    putString("tanggalSelesai", kelola.tanggalSelesai)
                    putString("gambarUrl", kelola.gambarUrl)
                }
                onItemClick(bundle)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<KelolaModel>() {
        override fun areItemsTheSame(oldItem: KelolaModel, newItem: KelolaModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: KelolaModel, newItem: KelolaModel): Boolean {
            return oldItem == newItem
        }
    }
}