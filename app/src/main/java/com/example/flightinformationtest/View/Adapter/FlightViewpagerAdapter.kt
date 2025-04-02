package com.example.flightinformationtest.View.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flightinformationtest.View.FlightView.ArrivalsFragment
import com.example.flightinformationtest.View.FlightView.DeparturesFragment

class FlightViewpagerAdapter(fragment: Fragment):FragmentStateAdapter(fragment) {
    private val flightListview= listOf(DeparturesFragment(),ArrivalsFragment())

    override fun getItemCount(): Int=flightListview.size
    override fun createFragment(position: Int): Fragment =flightListview[position]


}