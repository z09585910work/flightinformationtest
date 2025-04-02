package com.example.flightinformationtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.flightinformationtest.ModelView.ScreenViewModel
import com.example.flightinformationtest.View.CurrencyFragment
import com.example.flightinformationtest.View.FlightFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

   private lateinit var screenModel:ScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavguidetitle=findViewById<BottomNavigationView>(R.id.GuidedTitle)
        screenModel=ViewModelProvider(this).get(ScreenViewModel::class.java)

        screenModel.selectedFragment.observe(this){
            fragment->supportFragmentManager.beginTransaction()
            .replace(R.id.Screen_layout,fragment)
            .commit()
        }

        bottomNavguidetitle.setOnItemSelectedListener {
            item->when(item.itemId){
                R.id.navigation_flight->screenModel.selectFragment(FlightFragment())
                R.id.navigation_currency->screenModel.selectFragment(CurrencyFragment())

            }
            true
        }
    }


}