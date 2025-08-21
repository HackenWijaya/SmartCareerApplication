package com.example.project.basic_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.example.project.R


class FirstFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        val btntofragment2: Button = view.findViewById(R.id.btntofragment2)

        btntofragment2.setOnClickListener {
            (activity as? fragmentActivity)?.replaceFragment(SecondFragment())
        }
        return view
    }

}