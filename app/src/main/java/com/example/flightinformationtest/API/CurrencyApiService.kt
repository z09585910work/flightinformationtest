package com.example.flightinformationtest.API

import com.example.flightinformationtest.Data.CurrencyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("latest")
    fun getLatestRates(
        @Query("apikey") apiKey:String
    ):Call<CurrencyResponse>
}