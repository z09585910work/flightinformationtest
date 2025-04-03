package com.example.flightinformationtest.ModelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flightinformationtest.Data.FlightInfo
import com.example.flightinformationtest.Model.FlightRepository

class FlightViewModel : ViewModel() {

    private val repository = FlightRepository()
    private val _flights = MutableLiveData<List<FlightInfo>?>()
    val flights: LiveData<List<FlightInfo>?> get() = _flights

    fun loadFlights() {
        repository.fetchFlights { data ->
            _flights.postValue(data ?: emptyList())
        }
    }
}