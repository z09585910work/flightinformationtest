package com.example.flightinformationtest.ModelView

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightinformationtest.Model.CurrencyRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CurrencyViewModel: ViewModel() {

    private val repository = CurrencyRepository()
    val rates:LiveData<Map<String,Double>> get()=repository.rates
    var updateJobC: Job? =null

    //private val API_KEY="fca_live_1dJaKxoSAJGMkXq9YgvFP5VLJc6bkJR0QeidcoM2"
    private val API_KEY="fca_live_tjVSCZqtDkrZzVtbrDHhwcQ6owO3kLxHJ2hXAHpR"


    fun loadRates(apiKey:String){
        repository.fetchLatesRates(apiKey)
    }

    fun loadRate(){
        repository.fetchLatesRates(API_KEY)
        Log.d("CurrencyViewModel", "loadRate: "+rates)
    }

    fun startAutoUpdateC(secons:  Long=10000L){

        if(updateJobC?.isActive==true) return

        updateJobC=viewModelScope.launch {

            while (isActive){

                try {
                    loadRate()
                    delay(secons)
                    Log.d("CurrencyViewModel", "OKOKOK ")
                }catch (e:Exception){

                    Log.e("CurrencyViewModel", "Fetch error: ${e.message}")
                }
            }
        }
    }

    fun stopFetchingC(){
        updateJobC?.cancel()
    }

    override fun onCleared() {
        super.onCleared()

        updateJobC?.cancel()
    }

}