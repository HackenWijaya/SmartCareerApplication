package com.example.project.basic_api.ui.view.main.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project.R
import com.example.project.databinding.BottomSheetLayoutBinding
import com.example.project.databinding.FragmentMessageBinding
import com.example.project.databinding.FragmentMoreBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MoreFragment : Fragment() {
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMoreBinding.inflate(inflater,container,false)

        binding.fab.setOnClickListener {
            showBotomSheetDialog()
        }

        return binding.root
    }

    private fun showBotomSheetDialog(){
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bindingbottom = BottomSheetLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        bottomSheetDialog.setContentView(bindingbottom.root)
        bottomSheetDialog.show()
        initChips(bindingbottom)
        initDatePicker(bindingbottom)
        initSingleChoiceDialog(bindingbottom)
    }

    private fun initChips(binding2: BottomSheetLayoutBinding){
//        val chipGroup: ChipGroup = view.findViewById(R.id.chipGroup)

        val tags = listOf("tab chip 1","tab chip 2","tab chip 3","tab chip 4", )
        for (tag in tags){
            val chip = Chip(this.context).apply{
                text = tag
                isCheckable = true
            }
            binding2.chipGroup.addView(chip)
        }
    }

    private fun initDatePicker(binding: BottomSheetLayoutBinding){
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
//        val editTextDate: TextInputEditText = view.findViewById(R.id.InputDate)

        binding.InputDate.setOnClickListener(){
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Material Design Dialog")
                .setMessage("ini meruapakan contoh penerapan dialog versi material design")
                .setNeutralButton("batal"){dialog, which ->

                }
                .setNegativeButton("tidak"){dialog, which ->

                }
                .setPositiveButton("iya"){dialog, which ->
                    datePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")

                }
                .show()
        }
        datePicker.addOnPositiveButtonClickListener { selection ->
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val selectedDate = dateFormat.format(Date(selection))
            binding.InputDate.setText(selectedDate)
        }
    }

    private fun initSingleChoiceDialog(binding:BottomSheetLayoutBinding    ){
        val options = arrayOf("pilihan 1","pilihan 2", "pilihan 3" )
        val selectedOption = 0
//        val textOthers: TextInputEditText = view.findViewById(R.id.InputOthers)

        binding.InputOthers.setOnClickListener{
            showSingleChoiceDialog(options, selectedOption){ choice ->
                binding.InputOthers.setText(options[choice])

            }
        }
    }
    private fun showSingleChoiceDialog(
        options: Array<String>,
        checkedItem: Int,
        onChoiceSelected: (Int) -> Unit
    ){
        var tempSelectedOption = checkedItem
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Pilih opsi")
            .setSingleChoiceItems(options,checkedItem){dialog, which ->
                tempSelectedOption = which
            }
            .setPositiveButton("OK"){dialog, _->
                onChoiceSelected(tempSelectedOption)
                dialog.dismiss()
            }
            .setNegativeButton("Batal"){dialog, _->
                dialog.dismiss()
            }
            .show()
    }

}