package com.example.project.basic_api.ui.view.main.admin

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.example.project.R
import com.example.project.basic_api.data.model.KelolaModel
import com.example.project.basic_api.ui.adapter.KelolaAdapter
import com.example.project.basic_api.ui.viewmodel.KelolaViewModel
import com.example.project.basic_api.utils.Resource
import com.example.project.databinding.BottomAddKelolaSheetLayoutBinding
import com.example.project.databinding.FragmentKelolaBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class KelolaFragment : Fragment() {

    private var _binding: FragmentKelolaBinding? = null
    private val binding get() = _binding!!
    private lateinit var kelolaAdapter: KelolaAdapter
    private val kelolaViewModel: KelolaViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKelolaBinding.inflate(inflater, container, false)
        setupToolbar()
        setupRecyclerView()
        observeData()
        observeDeleteStatus()
        observeUpdateStatus()
        observeAddStatus()
        setupFab()

        kelolaViewModel.getKelola()

        return binding.root
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbarKelola
        toolbar.title = "List Lamaran"
        toolbar.setNavigationIcon(R.drawable.ic_back2)

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        kelolaAdapter = KelolaAdapter(
            kelolaList = mutableListOf(),
            onEditClick = { kelola ->
                showKelolaBottomSheet(kelola)
            },
            onDeleteClick = { kelola ->
                kelolaViewModel.deleteKelola(kelola.id)
                observeDeleteStatus()
            }
        )
        binding.recyclerKelola.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerKelola.adapter = kelolaAdapter
    }

    private fun observeData() {
        kelolaViewModel.kelolaData.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    kelolaAdapter.updateData(resource.data ?: emptyList())
                    Log.d("KelolaFragment", "Data berhasil diambil: ${resource.data}")
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Unexpected state!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun observeDeleteStatus() {
        kelolaViewModel.deleteKelolaStatus.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Menghapus data...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                    kelolaViewModel.getKelola() // Refresh data setelah penghapusan
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Gagal menghapus data: ${resource.message}", Toast.LENGTH_SHORT).show()
                }

                is Resource.Empty -> {}
            }
        })
    }

    private fun observeUpdateStatus() {
        kelolaViewModel.updateKelolaStatus.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Mengupdate data...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    kelolaViewModel.getKelola() // Refresh data setelah update
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Gagal memperbarui data: ${resource.message}", Toast.LENGTH_SHORT).show()
                }

                is Resource.Empty -> {}
            }
        })
    }

    private fun observeAddStatus() {
        kelolaViewModel.addKelolaStatus.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Menambahkan data...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    kelolaViewModel.getKelola() // Refresh data setelah penambahan
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Gagal menambahkan data: ${resource.message}", Toast.LENGTH_SHORT).show()
                }

                is Resource.Empty -> {}
            }
        })
    }

    private fun showKelolaBottomSheet(kelola: KelolaModel? = null) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = BottomAddKelolaSheetLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        if (kelola != null) {
            bottomSheetBinding.inputNamaKelola.setText(kelola.judul)
            bottomSheetBinding.inputDeskripsiKelola.setText(kelola.deskripsi)
            bottomSheetBinding.inputDetailKelola.setText(kelola.detail)
            bottomSheetBinding.inputSyaratKelola.setText(kelola.syarat)
            bottomSheetBinding.inputTanggalMulai.setText(kelola.tanggalMulai)
            bottomSheetBinding.inputTanggalSelesai.setText(kelola.tanggalSelesai)
            bottomSheetBinding.inputUrlGambar.setText(kelola.gambarUrl)
        }

        // Setup DatePicker for inputTanggalMulai
        bottomSheetBinding.inputTanggalMulai.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    bottomSheetBinding.inputTanggalMulai.setText("${selectedDay}/${selectedMonth + 1}/$selectedYear")
                },
                year, month, dayOfMonth
            )
            datePickerDialog.show()
        }

        // Setup DatePicker for inputTanggalSelesai
        bottomSheetBinding.inputTanggalSelesai.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    bottomSheetBinding.inputTanggalSelesai.setText("${selectedDay}/${selectedMonth + 1}/$selectedYear")
                },
                year, month, dayOfMonth
            )
            datePickerDialog.show()
        }

        bottomSheetBinding.btnSubmitUrl.setOnClickListener {
            val imageUrl = bottomSheetBinding.inputUrlGambar.text.toString()
            if (imageUrl.isNotEmpty()) {
                Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.ic_launcher_background)
                    .into(bottomSheetBinding.previewGambarKelola)
            } else {
                Toast.makeText(requireContext(), "URL gambar tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        bottomSheetBinding.btnSimpanKelola.setOnClickListener {
            val namaKelola = bottomSheetBinding.inputNamaKelola.text.toString()
            val deskripsiKelola = bottomSheetBinding.inputDeskripsiKelola.text.toString()
            val detailKelola = bottomSheetBinding.inputDetailKelola.text.toString()
            val syaratKelola = bottomSheetBinding.inputSyaratKelola.text.toString()
            val tanggalMulai = bottomSheetBinding.inputTanggalMulai.text.toString()
            val tanggalSelesai = bottomSheetBinding.inputTanggalSelesai.text.toString()

            if (namaKelola.isEmpty() || deskripsiKelola.isEmpty() || detailKelola.isEmpty() || syaratKelola.isEmpty() || tanggalMulai.isEmpty() || tanggalSelesai.isEmpty()) {
                Toast.makeText(requireContext(), "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                val kelolaModel = KelolaModel(
                    id = kelola?.id ?: "", // ID tetap digunakan saat update
                    deskripsi = deskripsiKelola,
                    detail = detailKelola,
                    gambarUrl = bottomSheetBinding.inputUrlGambar.text.toString(),
                    judul = namaKelola,
                    syarat = syaratKelola,
                    tanggalMulai = tanggalMulai,
                    tanggalSelesai = tanggalSelesai
                )

                if (kelola != null) {
                    kelolaViewModel.updateKelola(kelola.id, kelolaModel)
                } else {
                    kelolaViewModel.addKelola(kelolaModel)
                }
                bottomSheetDialog.dismiss()
            }
        }

        bottomSheetDialog.show()
    }


    private fun setupFab() {
        binding.fab.setOnClickListener {
            showKelolaBottomSheet()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}