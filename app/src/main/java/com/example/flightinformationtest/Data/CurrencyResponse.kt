package com.example.flightinformationtest.Data

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("data")var rates:CurrencyInfo
)
