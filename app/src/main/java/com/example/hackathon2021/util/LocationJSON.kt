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

    fun getLocations(query: String) : List<List<String>> {

        val genURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/$format" +
                "?keyword=${query.replace(" ", "+")}" +
                "&location=$latitude%2C$longitude" +
                "&radius=$radius" +
                "&key=$key"

        // needs to be async - can't do it on main thread
        val res = URL(genURL).readText()
        return parseResponse(res)
    }

    private fun parseResponse(jsonString: String) : List<List<String>>{

        val data: MutableList<MutableList<String>> = mutableListOf()

        val jsonObj = JSONObject(jsonString)
        val results = jsonObj.getJSONArray("results")

        for (i in 0 until results.length() - 1) {

            val itemData: MutableList<String> = mutableListOf()
            val name = results.getJSONObject(i).getString("name")
            val desc = "desc"
            val number = "number"
            val address = results.getJSONObject(i).getString("vicinity")
            val distance = "dist"
//            val open = results.getJSONObject(i).getJSONObject("opening_hours").getString("open_now")
            val open = "open"

            itemData.add(name)
            itemData.add(desc)
            itemData.add(number)
            itemData.add(address)
            itemData.add(distance)
            itemData.add(open)

            data.add(itemData)
        }

        return data
    }
}
