package com.example.project.basic_api.ui.view.main.lamaran

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.project.R
import com.example.project.basic_api.data.model.LamaranModel
import com.example.project.basic_api.ui.adapter.LamaranAdapter
import com.example.project.basic_api.ui.view.main.profil.ProfilFragment
import com.example.project.basic_api.ui.viewmodel.LamaranViewModel
import com.example.project.basic_api.utils.Resource
import com.example.project.databinding.FragmentLamaranBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class LamaranFragment : Fragment() {
    private var _binding: FragmentLamaranBinding? = null
    private val binding get() = _binding!!
    private lateinit var lamaranAdapter: LamaranAdapter
    private val lamaranViewModel: LamaranViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLamaranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter (for RecyclerView)
        lamaranAdapter = LamaranAdapter(mutableListOf()) { lamaran ->
            // Handle item click if needed
        }

        // Set the adapter to the RecyclerView
//        binding.recyclerViewLamaran.adapter = lamaranAdapter // Assuming your RecyclerView's ID is recyclerViewLamaran

        // Initialize the spinner for gender selection
        val spinner: Spinner = binding.spinnerJenisKelamin
        val jenisKelamin = arrayOf("Pria", "Wanita")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, jenisKelamin)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Initialize the DatePickerDialog for the birth date field
        binding.TanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.TanggalLahir.setText(selectedDate)
                },
                year, month, dayOfMonth
            )
            datePickerDialog.show()
        }

        // Handle the submit button click event
        binding.btnSubmit.setOnClickListener {
            // Collect data from the form
            val name = binding.NamaLengkap.text.toString()
            val tempatLahir = binding.Tempat.text.toString()
            val tanggalLahir = binding.TanggalLahir.text.toString()
            val jenisKelamin = binding.spinnerJenisKelamin.selectedItem.toString()
            val suratLamaran = binding.suratlamaran.text.toString()
            val cv = binding.cv.text.toString()

            // Check if any required field is empty
            if (name.isEmpty() || tempatLahir.isEmpty() || tanggalLahir.isEmpty() || suratLamaran.isEmpty() || cv.isEmpty()) {
                Snackbar.make(requireView(), "Semua field harus diisi!", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentUser = FirebaseAuth.getInstance().currentUser
            val uid = currentUser?.uid
            if (uid == null) {
                Snackbar.make(requireView(), "Gagal mendapatkan UID pengguna!", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Create a LamaranModel object with the collected data
            val lamaran = LamaranModel(
                uid = uid,
                name = name,
                tempatlahir = tempatLahir,
                tgllahir = tanggalLahir,
                jk = jenisKelamin,
                surat = suratLamaran,
                cv = cv
            )

            lamaranViewModel.addLamaran(lamaran)


        }

        // Observe ViewModel data
        observeData()
        observeDeleteStatus()
        observeUpdateStatus()
        observeAddStatus()
    }

    private fun observeData() {
        lamaranViewModel.lamaranData.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show loading indicator if needed
                }
                is Resource.Success -> {
                    lamaranAdapter.updateData(resource.data ?: emptyList()) // Now safe to use
                    Log.d("LamaranFragment", "Data berhasil diambil: ${resource.data}")
                }
                is Resource.Error -> {
                    Snackbar.make(requireView(), "Error: ${resource.message}", Snackbar.LENGTH_SHORT).show()
                }
                else -> {
                    Snackbar.make(requireView(), "Unexpected state!", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun observeDeleteStatus() {
        lamaranViewModel.deleteLamaranStatus.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Menghapus data...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                    lamaranViewModel.getLamaran() // Refresh data setelah penghapusan
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Gagal menghapus data: ${resource.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Empty -> {}
            }
        })
    }

    private fun observeUpdateStatus() {
        lamaranViewModel.updateLamaranStatus.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Mengupdate data...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    lamaranViewModel.getLamaran() // Refresh data setelah update
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Gagal memperbarui data: ${resource.message}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Empty -> {}
            }
        })
    }

    private fun observeAddStatus() {
        lamaranViewModel.addLamaranStatus.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Snackbar.make(requireView(), "Menambahkan data...", Snackbar.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Snackbar.make(requireView(), "Data berhasil ditambahkan", Snackbar.LENGTH_SHORT).show()
                    lamaranViewModel.getLamaran() // Refresh data setelah penambahan
                }
                is Resource.Error -> {
                    Snackbar.make(requireView(), "Gagal menambahkan data: ${resource.message}", Snackbar.LENGTH_SHORT).show()
                }
                is Resource.Empty -> {}
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leak
    }
}
