package com.example.flightinformationtest.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flightinformationtest.API.CurrencyClient
import com.example.flightinformationtest.Data.CurrencyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyRepository {

    private val _rates = MutableLiveData<Map<String, Double>>(emptyMap())
    val rates: LiveData<Map<String, Double>> get() = _rates
    private val API_KEY = "fca_live_EH2rjenCF9rUcGL74gGt93tD1CzEOhEP7est4z7Q"

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchLatesRates(CURR: String) {
        val currencies = "USD,JPY,CNY,EUR,AUD,KRW"
        val baseCurrency = CURR  // 你可以換成任何你要的 base，比如 JPY、USD 等
        CurrencyClient.instance.getLatestRates(API_KEY,currencies,baseCurrency).enqueue(object : Callback<CurrencyResponse> {
            override fun onResponse(
                call: Call<CurrencyResponse>,
                response: Response<CurrencyResponse>
            ) {

                if (response.isSuccessful) {
                    response.body()?.rates?.let { rate ->
                        _rates.value = mapOf(

                            "USD" to rate.USD,
                            "JPY" to rate.JPY,
                            "CNY" to rate.CNY,
                            "EUR" to rate.EUR,
                            "AUD" to rate.AUD,
                            "KRW" to rate.KRW,
                            )
                    } ?: run {
                        _rates.value = emptyMap()
                    }

                    Log.d("API連線狀態", response.isSuccessful.toString())

                } else {
                    _error.value = "API 回應錯誤: ${response.code()}"

                    Log.d("API連線狀態", response.isSuccessful.toString() + "  " + _error.value)
                }

            }

            override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                t.printStackTrace()
                _error.value = t.localizedMessage ?: "未知錯誤"
            }
        })
    }
}