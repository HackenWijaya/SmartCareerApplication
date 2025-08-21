package com.example.project.welcome_screen

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.project.basic_fragment.FirstFragment

class pageradapter (
    activity: WelcomescreenActivity,
    private val fragment: List<Fragment>
) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return fragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragment[position]
    }
}