package com.example.flightinformationtest.ModelView

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightinformationtest.Model.CurrencyRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository()
    val rates: LiveData<Map<String, Double>> get() = repository.rates
    var updateJobC: Job? = null

    private var _inputAmount = MutableLiveData<Double>()
    val inputAmount: LiveData<Double> get() = _inputAmount

    private val _convertedRates = MutableLiveData<Map<String, Double>>()
    val convertedRates: LiveData<Map<String, Double>> get() = _convertedRates

    private val _showCalculatorEvent = MutableLiveData<View?>()
    val showCalculatorEvent: LiveData<View?> get() = _showCalculatorEvent

    val currList= listOf("USD","JPY","CNY","EUR","AUD","KRW")



    fun loadCURRRates(CURR: Int,baseAmount:Double) {
        val baseCurrency=currList[CURR]
        val baseRates=baseAmount

        repository.fetchLatesRates(baseCurrency)
        repository.rates.observeForever{ reatsMap ->

            if(reatsMap.isNotEmpty()){
                setInputAmount(baseRates)
            }

        }


    }

    fun triggerCalculator(anchorView: View) {
        _showCalculatorEvent.value = anchorView
    }

    fun clearCalculatorEvent() {
        _showCalculatorEvent.value = null
    }

    fun setInputAmount(amount: Double) {

        _inputAmount.value = amount
        CoverRates()
    }

    private fun CoverRates() {
        val baseAmount = _inputAmount.value ?: return
        val currentRates = rates.value ?: return

        val converted = currentRates.mapValues { it.value * baseAmount }
        _convertedRates.value=converted

        Log.d("CurrencyViewModel","CoverRates(): $converted")
    }


    fun loadRate() {
        repository.fetchLatesRates("USD")

        Log.d("CurrencyViewModel", "loadRate: " + rates)
    }

    fun startAutoUpdateC(secons: Long = 10000L) {

        if (updateJobC?.isActive == true) return

        updateJobC = viewModelScope.launch {

            while (isActive) {

                try {
                    loadRate()
                    delay(secons)
                    Log.d("CurrencyViewModel", "OKOKOK ")
                } catch (e: Exception) {

                    Log.e("CurrencyViewModel", "Fetch error: ${e.message}")
                }
            }
        }
    }

    fun stopFetchingC() {
        updateJobC?.cancel()
    }

    override fun onCleared() {
        super.onCleared()

        updateJobC?.cancel()
    }

}