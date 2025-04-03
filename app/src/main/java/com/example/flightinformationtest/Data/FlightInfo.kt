package com.example.flightinformationtest.Data

data class FlightInfo(
    val expectTime: String,
    val realTime: String,
    val airLineName: String,
    val airLineCode: String,
    val airLineLogo: String,
    val airLineUrl: String,
    val airLineNum: String,
    val upAirportCode: String,
    val upAirportName: String,
    val airPlaneType: String,
    val airBoardingGate: String?,
    val airFlyStatus: String,
    val airFlyDelayCause: String?
)
