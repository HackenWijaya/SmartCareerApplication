package com.example.project.basic_api.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.basic_api.data.model.KelolaModel
import com.example.project.databinding.ItemKelolaBinding
import com.squareup.picasso.Picasso

class KelolaAdapter(
    private var kelolaList: MutableList<KelolaModel>,
    private val onEditClick: (KelolaModel) -> Unit,
    private val onDeleteClick: (KelolaModel) -> Unit
) : RecyclerView.Adapter<KelolaAdapter.KelolaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KelolaViewHolder {
        val binding = ItemKelolaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KelolaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KelolaViewHolder, position: Int) {
        val kelola = kelolaList[position]
        holder.bind(kelola)
    }

    override fun getItemCount(): Int = kelolaList.size

    fun updateData(newList: List<KelolaModel>) {
        kelolaList.clear()
        kelolaList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class KelolaViewHolder(private val binding: ItemKelolaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(kelola: KelolaModel) {
            binding.tvJudul.text = kelola.judul
            binding.tvDeskripsi.text = kelola.deskripsi
            binding.tvDetail.text = kelola.detail
            binding.tvSyarat.text = kelola.syarat
            binding.tvTanggalMulai.text = kelola.tanggalMulai
            binding.tvTanggalSelesai.text = kelola.tanggalSelesai

            Picasso.get()
                .load(kelola.gambarUrl)
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.ivGambarEvent)

            binding.btnEdit.setOnClickListener {
                onEditClick(kelola) // Callback untuk tombol Edit
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClick(kelola) // Callback untuk tombol Delete
            }
        }
    }
}