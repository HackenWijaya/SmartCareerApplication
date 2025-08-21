package com.example.project.basic_api.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project.basic_api.data.model.LamaranModel
import com.example.project.databinding.FragmentLamaranBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class LamaranAdapter(
    private var lamaranList: MutableList<LamaranModel>,
    private val submit: (LamaranModel) -> Unit,
) : RecyclerView.Adapter<LamaranAdapter.LamaranViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LamaranViewHolder {
        val binding = FragmentLamaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LamaranViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LamaranViewHolder, position: Int) {
        val lamaran = lamaranList[position]
        holder.bind(lamaran)
    }

    override fun getItemCount(): Int = lamaranList.size

    fun updateData(newList: List<LamaranModel>) {
        lamaranList.clear()
        lamaranList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class LamaranViewHolder(private val binding: FragmentLamaranBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(lamaran: LamaranModel) {
            binding.NamaLengkap.setText(lamaran.name)
            binding.Tempat.setText(lamaran.tempatlahir)
            binding.TanggalLahir.setText(lamaran.tgllahir) // Set tanggal awal

            // Untuk spinner, Anda dapat menampilkan data langsung sebagai teks
            val genderList = listOf("Pria", "Wanita")
            val selectedGenderIndex = genderList.indexOf(lamaran.jk)
            if (selectedGenderIndex >= 0) {
                binding.spinnerJenisKelamin.setSelection(selectedGenderIndex)
            }
            // Surat lamaran dan CV
            binding.suratlamaran.setText(lamaran.surat)
            binding.cv.setText(lamaran.cv)

            // DatePicker untuk TanggalLahir
            binding.TanggalLahir.setOnClickListener {
                showDatePicker { selectedDate ->
                    binding.TanggalLahir.setText(selectedDate) // Update EditText dengan tanggal yang dipilih
                    lamaran.tgllahir = selectedDate // Update model dengan tanggal baru
                }
            }

            // Callback untuk tombol Submit
            binding.btnSubmit.setOnClickListener {
                submit(lamaran)
            }
        }

        // Fungsi untuk menampilkan Material DatePicker
        private fun showDatePicker(onDateSelected: (String) -> Unit) {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.show((binding.root.context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = sdf.format(Date(selection))
                onDateSelected(formattedDate)
            }
        }
    }
}
