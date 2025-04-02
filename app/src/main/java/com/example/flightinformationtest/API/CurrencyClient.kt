package com.example.flightinformationtest.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CurrencyClient {

    private const val  BASE_URL="https://api.freecurrencyapi.com/v1/"

    val instance: CurrencyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApiService::class.java)
    }
}