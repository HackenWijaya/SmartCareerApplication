package com.example.project.basic_api.ui.view.main.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.project.LoginActivity
import com.example.project.R
import com.example.project.basic_api.ui.viewmodel.LamaranViewModel
import com.example.project.basic_api.utils.Resource
import com.example.project.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val lamaranViewModel: LamaranViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inisialisasi view binding
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil SharedPreferences untuk username
        val sharedPref = requireContext().getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        val username2 = sharedPref.getString("username", null)

        val displayName = username2?.split("@")?.get(0) ?: "Pengguna"
        binding.namaprofile.text = displayName
        binding.namaprofileatas.text = displayName
        binding.displayemail.text = username2


        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        if (uid.isNotEmpty()) {
            lamaranViewModel.getStatusByUid(uid)
        }

        binding.btnlogoutprofile.setOnClickListener {
            context?.let { ctx ->
                AlertDialog.Builder(ctx)
                    .setTitle("LogOut")
                    .setMessage("Apakah anda yakin ingin keluar?")
                    .setPositiveButton("Ya") { dialogInterface, _ ->
                        // Hapus semua data dari SharedPreferences
                        val sharPref = ctx.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
                        val editor = sharPref.edit()
                        editor.clear()
                        editor.apply()

                        // Pindah ke LoginActivity
                        val intent = Intent(ctx, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        // Log out dari Firebase
                        FirebaseAuth.getInstance().signOut()

                        // Tutup dialog
                        dialogInterface.dismiss()
                    }
                    .setNegativeButton("Tidak", null)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Kosongkan binding saat view dihancurkan
        _binding = null
    }
}
