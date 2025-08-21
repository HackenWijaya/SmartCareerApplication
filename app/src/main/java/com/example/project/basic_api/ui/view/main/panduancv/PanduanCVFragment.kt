package com.example.project.basic_api.ui.view.main.panduancv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.R
import com.example.project.databinding.FragmentPanduanCVBinding

class PanduanCVFragment : Fragment() {

    private lateinit var binding: FragmentPanduanCVBinding
    private lateinit var adapter: ItemAdapterViewCV

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using ViewBinding
        binding = FragmentPanduanCVBinding.inflate(inflater, container, false)
        setupToolbar()

        // Initialize RecyclerView
        binding.recylerview.layoutManager = LinearLayoutManager(requireContext())

        // List of items to be displayed
        val items = listOf(
            ItemModelCV("Informasi Pribadi", "Mulailah CV dengan memberikan informasi pribadi yang jelas dan terkini, seperti nama lengkap, alamat, nomor telepon, dan alamat email."),
            ItemModelCV("Ringkasan Profil", "Tambahkan ringkasan profil singkat yang menyoroti keahlian dan pengalaman terkait dengan posisi yang dilamar."),
            ItemModelCV("Riwayat Pendidikan", "Catat riwayat pendidikanmu dengan urutan terbalik, dimulai dari pendidikan terakhir yang kamu ikuti."),
            ItemModelCV("Pengalaman Kerja", "Jelaskan pengalaman kerjamu dengan rinci. Cantumkan nama perusahaan, posisi yang dipegang, tanggal mulai dan berakhirnya, tanggung jawab dan pencapaian selama bekerja di sana."),
            ItemModelCV("Keahlian dan Kualifikasi", "Sebutkan keterampilan dan kemampuan yang kamu miliki, baik yang bersifat teknis maupun yang bersifat interpersonal."),
            ItemModelCV("Sertifikasi dan Pelatihan", "Jika kamu memiliki sertifikasi atau pelatihan yang relevan dengan pekerjaan yang kamu lamar, cantumkan dalam bagian ini.")
        )

        // Initialize the adapter and pass a click listener
        adapter = ItemAdapterViewCV(items) { item ->
            // Handle the click here
            onItemClick(item)
        }
        binding.recylerview.adapter = adapter

        return binding.root
    }
    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.title = "Panduan Pembuatan CV"
        toolbar.setNavigationIcon(R.drawable.ic_back2)

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun onItemClick(item: ItemModelCV) {
        when (item.name) {
            "Informasi Pribadi" -> {
                val showPopUp = popup1Fragment()
                showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
            }
            "Ringkasan Profil" -> {
                val showPopUp = popup2Fragment()
                showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
            }
            "Riwayat Pendidikan" -> {
                val showPopUp = popup3Fragment()
                showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
            }
            "Pengalaman Kerja" -> {
                val showPopUp = popup4Fragment()
                showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
            }
            "Keahlian dan Kualifikasi" -> {
                val showPopUp = popup5Fragment()
                showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
            }
            "Sertifikasi dan Pelatihan" -> {
                val showPopUp = popup6Fragment()
                showPopUp.show((activity as AppCompatActivity).supportFragmentManager, "showPopUp")
            }
        }
    }
}
