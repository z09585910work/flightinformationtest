package com.example.flightinformationtest.ModelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.flightinformationtest.Model.CurrencyRepository

class CurrencyViewModel: ViewModel() {

    private val repository = CurrencyRepository()
    val rates:LiveData<Map<String,Double>> get()=repository.rates

    fun loadRates(apiKey:String){
        repository.fetchLatesRates(apiKey)
    }
}