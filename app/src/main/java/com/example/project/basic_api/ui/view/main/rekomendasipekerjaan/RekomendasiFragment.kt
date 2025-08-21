package com.example.project.basic_api.ui.view.main.rekomendasipekerjaan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.R
import com.example.project.basic_api.data.repository.KelolaRepository
import com.example.project.basic_api.utils.Resource
import com.example.project.databinding.FragmentRekomendasiBinding
import kotlinx.coroutines.launch

class RekomendasiFragment : Fragment() {

    private var _binding: FragmentRekomendasiBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: KelolaUserAdapter
    private val repository = KelolaRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRekomendasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchKelolaData()
    }

    private fun setupRecyclerView() {
        adapter = KelolaUserAdapter { bundle ->
            navigateToDetail(bundle)
        }
        binding.recyclerViewKelola.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@RekomendasiFragment.adapter
        }
    }

    private fun fetchKelolaData() {
        lifecycleScope.launch {
            when (val result = repository.getKelola()) {
                is Resource.Success -> {
                    adapter.submitList(result.data)
                }
                is Resource.Error -> {
                    // Handle error (e.g., show a Toast or Snackbar)
                }
                is Resource.Empty -> {
                    // Handle empty state if needed
                }
                is Resource.Loading -> {
                    // Handle loading state if needed
                }
            }
        }
    }

    private fun navigateToDetail(bundle: Bundle) {
        val detailFragment = DetailFragment().apply {
            arguments = bundle
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}