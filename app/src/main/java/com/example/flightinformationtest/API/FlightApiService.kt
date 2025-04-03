package com.example.flightinformationtest.API

import com.example.flightinformationtest.Data.FlightInfo
import com.example.flightinformationtest.Data.FlightResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightApiService {
    @GET("InstantSchedule.ashx?AirFlyLine=2&AirFlyIO=2")
    fun getFlights(): Call<FlightResponse>
}