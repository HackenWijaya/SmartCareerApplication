package com.example.project.basic_api.ui.view.main.rekomendasi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.project.R

class HasilFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hasil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve Views
        val hasil: TextView = view.findViewById(R.id.hasil)
        val button: Button = view.findViewById(R.id.button)

        // Handle Button Click to Navigate Back to MainActivity
        button.setOnClickListener{
            dismiss()
        }

        // Retrieve SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("HasilPreferences", Context.MODE_PRIVATE)
        val savedResult = sharedPreferences.getString("hasil", "No result found")

        // Set the retrieved result in a TextView
        hasil.text = savedResult
    }
}
