package com.example.project.basic_api.ui.view.main.panduancv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.project.R

class popup4Fragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_popup4, container, false)
        val but = view.findViewById<Button>(R.id.button)

        but.setOnClickListener{
            dismiss()
        }
        return view
    }

}