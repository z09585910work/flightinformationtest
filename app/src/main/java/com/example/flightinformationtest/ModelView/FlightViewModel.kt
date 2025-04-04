package com.example.flightinformationtest.ModelView

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightinformationtest.Data.FlightInfo
import com.example.flightinformationtest.Model.FlightRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class FlightViewModel : ViewModel() {

    private val repository = FlightRepository()
    private val _flights = MutableLiveData<List<FlightInfo>?>()
    val flights: LiveData<List<FlightInfo>?> get() = _flights

    private var updateJob: Job? = null

    init {
        startAutoUpdate()// 啟動 10 秒自動更新
    }

     fun loadFlights() {
        repository.fetchFlights { data ->

            if(data != null){
                _flights.postValue(data ?: emptyList())
                Log.d("FlightViewModel", "loadFlights data: "+data)
            }

        }
    }

    fun startAutoUpdate(intervalMillis: Long = 10000L) {

        if(updateJob?.isActive==true) return// 確保不重複啟動

        updateJob = viewModelScope.launch {

            while (isActive) {

                try {
                    loadFlights()
                    //val data=repository.

                    delay(intervalMillis)
                } catch (e: Exception) {

                    Log.e("FlightViewModel", "Fetch error: ${e.message}")
                }

                Log.d("FlightViewModel", "OKOKOK")

            }
        }

    }

    fun stopFetching(){
        updateJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        updateJob?.cancel()
    }

}