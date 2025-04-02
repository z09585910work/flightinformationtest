package com.example.flightinformationtest.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.flightinformationtest.R
import com.example.flightinformationtest.View.Adapter.FlightViewpagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * A fragment representing a list of Items.
 */
class FlightFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flight, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val flightViewpager:ViewPager2=view.findViewById(R.id.Flight_Viewpager)
        val flightTabl:TabLayout=view.findViewById(R.id.Flight_table)

        val adpater=FlightViewpagerAdapter(this)
        flightViewpager.adapter=adpater

        TabLayoutMediator(flightTabl,flightViewpager){
            tab,position->tab.text=when(position){
                0->"起飛航班"
                1->"抵達航班"
                else->""
            }
        }.attach()

    }
}