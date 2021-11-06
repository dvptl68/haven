package com.example.hackathon2021.util

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL
import org.json.*

object LocationsJSON {

    private var latitude = 0.0
    private var longitude = 0.0
    private var format = "json"
    private var radius = 8000
    private var key = "AIzaSyDL4XY2UBOfKnK1BZWVU5X-3GCvxoonEvc"

    init {  }

    fun setCoords(lat : Double, long : Double) {
        latitude = lat
        longitude = long
    }

    fun setDefaultCoords() {
        latitude = 39.99786
        longitude = -83.0084521
    }

    fun getLocations(query: String) {

        var genURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/$format" +
                "?keyword=${query.replace(" ", "+")}" +
                "&location=$latitude%2C$longitude" +
                "&radius=$radius" +
                "&key=$key"
        println(genURL)

        GlobalScope.launch {
            val res = URL(genURL).readText()
            parseResponse(res)
        }
    }

    private fun parseResponse(json: String) : List<List<String>>{

        var data: MutableList<MutableList<String>> = mutableListOf()
        data.add(mutableListOf("a", "b"))
        println(data)

        return data
    }
}
