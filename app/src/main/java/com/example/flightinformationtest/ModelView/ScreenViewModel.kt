package com.example.flightinformationtest.ModelView

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flightinformationtest.View.FlightFragment

class ScreenViewModel : ViewModel() {

    private val _selectedFragment = MutableLiveData<Fragment>()
    val selectedFragment: LiveData<Fragment> = _selectedFragment

    init {
        _selectedFragment.value = FlightFragment()
    }

    fun selectFragment(fragment: Fragment){

        _selectedFragment.value=fragment
    }
}