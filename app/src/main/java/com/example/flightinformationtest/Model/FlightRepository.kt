package com.example.flightinformationtest.Model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.flightinformationtest.API.FlightApiService
import com.example.flightinformationtest.Data.FlightInfo
import com.example.flightinformationtest.Data.FlightResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlightRepository {

    private val flightApi: FlightApiService
    private val BASE_URL = "https://www.kia.gov.tw/API/"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        flightApi = retrofit.create(FlightApiService::class.java)
    }

    private val _error = MutableLiveData<String?>()

    fun fetchFlights(callback: (List<FlightInfo>?) -> Unit) {

        flightApi.getFlights().enqueue(object : Callback<FlightResponse> {
            override fun onResponse(
                call: Call<FlightResponse>,
                response: Response<FlightResponse>
            ) {
                if (response.isSuccessful) {
                    val flights = response.body()?.InstantSchedule ?: emptyList()
                    callback(flights)
                } else {
                    callback(emptyList())  // 失敗時回傳空列表

                    _error.value = "API 回應錯誤: ${response.code()}"
                    Log.d(
                        "response.isSuccessful:",
                        response.isSuccessful.toString() + " " + _error.value
                    )
                }
            }

            override fun onFailure(call: Call<FlightResponse>, t: Throwable) {
                callback(emptyList())
                Log.d("response.isSuccessful:", t.printStackTrace().toString())
            }

        })

    }


}