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

    private  val _rates=MutableLiveData<Map<String,Double>>(emptyMap())
    val rates:LiveData<Map<String,Double>> get() = _rates

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchLatesRates(apiKey:String){
        CurrencyClient.instance.getLatestRates(apiKey).enqueue(object : Callback<CurrencyResponse>{
            override fun onResponse(
                call: Call<CurrencyResponse>,
                response: Response<CurrencyResponse>
            ) {

                if (response.isSuccessful){
                    _rates.value=response.body()?.rates ?: emptyMap()

                    Log.d("API連線狀態",response.isSuccessful.toString())

                }else{
                    _error.value = "API 回應錯誤: ${response.code()}"

                    Log.d("API連線狀態",response.isSuccessful.toString()+"  "+_error.value)
                }


            }

            override fun onFailure(call: Call<CurrencyResponse>, t: Throwable) {
                t.printStackTrace()
                _error.value = t.localizedMessage ?: "未知錯誤"
            }
        })
    }
}