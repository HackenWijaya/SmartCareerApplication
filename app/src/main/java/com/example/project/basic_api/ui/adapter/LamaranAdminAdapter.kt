package com.example.project.basic_api.ui.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.basic_api.data.model.LamaranModel
import com.example.project.databinding.ItemLamaranBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class LamaranAdminAdapter(
    private var lamaranList: List<LamaranModel>,
    private val onStatusUpdated: () -> Unit // Callback untuk notifikasi saat status diperbarui
) : RecyclerView.Adapter<LamaranAdminAdapter.LamaranViewHolder>() {

    inner class LamaranViewHolder(val binding: ItemLamaranBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LamaranViewHolder {
        val binding = ItemLamaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LamaranViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LamaranViewHolder, position: Int) {
        val lamaran = lamaranList[position]
        with(holder.binding) {
            // Set data ke tampilan
            tvName.setText("Nama: " + lamaran.name)
            tvJk.setText("Jenis Kelamin: " + lamaran.jk)
            tvTempatLahir.setText("Tempat Lahir: " + lamaran.tempatlahir)
            tvTglLahir.setText("Tanggal Lahir: " + lamaran.tgllahir)
            tvStatus.setText("Status: " + lamaran.status)

            // Tombol untuk CV
            btnCv.setOnClickListener {
                showImagePopup(root.context, lamaran.cv)
            }

            btnSurat.setOnClickListener {
                showImagePopup(root.context, lamaran.surat)
            }

            // Atur visibilitas tombol berdasarkan status
            if (lamaran.status == "Diterima" || lamaran.status == "Ditolak") {
                btnTerima.visibility = View.GONE
                btnTolak.visibility = View.GONE
            } else {
                btnTerima.visibility = View.VISIBLE
                btnTolak.visibility = View.VISIBLE
            }

            // Tombol Terima
            btnTerima.setOnClickListener {
                updateStatus(lamaran.id, "Diterima", holder.adapterPosition, root.context)
            }

            // Tombol Tolak
            btnTolak.setOnClickListener {
                updateStatus(lamaran.id, "Ditolak", holder.adapterPosition, root.context)
            }
        }
    }

    override fun getItemCount(): Int = lamaranList.size

    private fun openUrlInBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    private fun showImagePopup(context: Context, url: String) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.popup_image)
        val imageView = dialog.findViewById<ImageView>(R.id.popupImageView)

        Picasso.get()
            .load(url)
            .error(R.drawable.ic_lowongan)
            .into(imageView)

        dialog.setCancelable(true)
        dialog.show()
    }

    private fun updateStatus(
        documentId: String,
        newStatus: String,
        position: Int,
        context: Context
    ) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("lamaran").document(documentId)
            .update("status", newStatus)
            .addOnSuccessListener {
                Toast.makeText(context, "Status diperbarui menjadi $newStatus", Toast.LENGTH_SHORT)
                    .show()
                lamaranList[position].status = newStatus
                notifyItemChanged(position)
                onStatusUpdated()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Gagal memperbarui status: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    // Fungsi untuk memperbarui daftar data di adapter
    fun updateData(newList: List<LamaranModel>) {
        lamaranList = newList
        notifyDataSetChanged()
    }
}
