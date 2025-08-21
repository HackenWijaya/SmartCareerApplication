package com.example.project.basic_api.ui.view.main.message

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MessageTabAdapter (activity: FragmentActivity): FragmentStateAdapter(activity){
    private val fragmentList = listOf(
        NotifikasiFragment(),
        InformasiFragment()
    )

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}