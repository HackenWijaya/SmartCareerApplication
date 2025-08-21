package com.example.project.basic_api.ui.view.main.profil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.LoginActivity
import com.example.project.basic_api.ui.adapter.ProfileAdapter
import com.example.project.basic_api.ui.viewmodel.LamaranViewModel
import com.example.project.basic_api.utils.Resource
import com.example.project.databinding.FragmentProfilBinding
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    private val lamaranViewModel: LamaranViewModel by viewModel()

    private lateinit var profilAdapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupProfile()

        setupRecyclerView()

        observeLamaranData()

        setupLogoutButton()

        fetchDataForUser()
    }

    private fun setupProfile() {
        val sharedPref = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val username2 = sharedPref.getString("username", null)

        val displayName = username2?.split("@")?.get(0) ?: "Pengguna"
        binding.namaprofile.text = displayName

        binding.displayemail.text = username2
    }

    private fun setupRecyclerView() {
        profilAdapter = ProfileAdapter(emptyList())
        binding.recyclerViewLamaran.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = profilAdapter
        }
    }

    private fun observeLamaranData() {
        lamaranViewModel.lamaranData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    profilAdapter.updateData(resource.data ?: emptyList())
                    binding.recyclerViewLamaran.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                }
                is Resource.Empty -> {
                }
            }
        }
    }

    private fun fetchDataForUser() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            lamaranViewModel.fetchLamaranByUserId()
        } else {
        }
    }

    private fun setupLogoutButton() {
        binding.btnlogoutprofile.setOnClickListener {
            context?.let { ctx ->
                AlertDialog.Builder(ctx)
                    .setTitle("LogOut")
                    .setMessage("Apakah anda yakin ingin keluar?")
                    .setPositiveButton("Ya") { dialogInterface, _ ->
                        val sharPref = ctx.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                        sharPref.edit().clear().apply()

                        val intent = Intent(ctx, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        FirebaseAuth.getInstance().signOut()

                        dialogInterface.dismiss()
                    }
                    .setNegativeButton("Tidak", null)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}