package com.example.project.basic_api.ui.view.main.rekomendasipekerjaan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project.R
import com.example.project.basic_api.ui.view.main.lamaran.LamaranFragment
import com.example.project.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil data dari Bundle
        val judul = arguments?.getString("judul")
        val deskripsi = arguments?.getString("deskripsi")
        val detail = arguments?.getString("detail")
        val syarat = arguments?.getString("syarat")
        val tanggalMulai = arguments?.getString("tanggalMulai")
        val tanggalSelesai = arguments?.getString("tanggalSelesai")
        val gambarUrl = arguments?.getString("gambarUrl")

        // Menampilkan data ke dalam UI
        binding.textViewTitle.text = judul
        binding.textViewDescription.text = deskripsi
        binding.textViewDetail.text = detail
        binding.textViewSyarat.text = syarat
        binding.textViewTanggal.text = "$tanggalMulai - $tanggalSelesai"

        Picasso.get()
            .load(gambarUrl)
            .error(com.example.project.R.drawable.ic_user) // Gambar error
            .into(binding.imageViewDetail)

        binding.buttonOpenForm.setOnClickListener {
            val lamaranFragment = LamaranFragment() // Buat instance dari fragment tujuan

            // Mulai transaksi fragment
            val transaction = parentFragmentManager.beginTransaction()

            // Ganti fragment saat ini dengan LamaranFragment
            transaction.replace(R.id.fragment_container, lamaranFragment)

            // Tambahkan transaksi ke back stack (opsional agar bisa kembali)
            transaction.addToBackStack(null)

            // Commit transaksi
            transaction.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}