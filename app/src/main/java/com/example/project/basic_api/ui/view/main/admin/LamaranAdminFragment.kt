package com.example.project.basic_api.ui.view.main.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.R
import com.example.project.basic_api.ui.adapter.LamaranAdminAdapter
import com.example.project.basic_api.ui.viewmodel.LamaranAdminViewModel
import com.example.project.databinding.FragmentLamaranAdminBinding
import com.google.android.material.chip.Chip

class LamaranAdminFragment : Fragment() {
    private var _binding: FragmentLamaranAdminBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LamaranAdminViewModel by viewModels()
    private lateinit var adapter: LamaranAdminAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLamaranAdminBinding.inflate(inflater, container, false)
        setupToolbar()
        return binding.root
    }
    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.title = "Kelola Lowongan"
        toolbar.setNavigationIcon(R.drawable.ic_back2)

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()

        // Memuat data lamaran terlebih dahulu
        viewModel.loadLamaranData()

        // Memuat filter opsi setelah data tersedia
        viewModel.lamaranListLiveData.observe(viewLifecycleOwner) { lamaranList ->
            if (lamaranList.isNotEmpty()) {
                viewModel.loadFilterOptions() // Memuat filter setelah data tersedia
                setupDynamicChips() // Setup chips setelah data tersedia
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = LamaranAdminAdapter(
            lamaranList = emptyList(),
            onStatusUpdated = {
                viewModel.loadLamaranData() // Muat ulang data setelah status diperbarui
            }
        )
        binding.recyclerViewLamaran.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewLamaran.adapter = adapter
    }

    private fun setupDynamicChips() {
        viewModel.filterOptions.observe(viewLifecycleOwner) { options ->
            binding.chipGroupFilter.removeAllViews()
            options.forEach { option ->
                val chip = createChip(option)
                binding.chipGroupFilter.addView(chip)

                if (option == "Semua") {
                    chip.isChecked = true
                    viewModel.filterLamaran(option) // Tampilkan semua data di awal
                }
            }
        }
    }

    private fun createChip(option: String): Chip {
        val chip = Chip(requireContext())
        chip.text = option
        chip.isClickable = true
        chip.isCheckable = true
        chip.setOnClickListener {
            Toast.makeText(requireContext(), "$option dipilih", Toast.LENGTH_SHORT).show()
            viewModel.filterLamaran(option)
        }
        return chip
    }

    private fun observeViewModel() {
        viewModel.filteredLamaranLiveData.observe(viewLifecycleOwner) { lamaranList ->
            adapter.updateData(lamaranList) // Update data RecyclerView setelah filter diterapkan
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}