package com.example.hackathon2021.util

import java.net.URL
import org.json.*
import kotlin.math.*

object LocationsJSON {

    private var latitude = 0.0
    private var longitude = 0.0
    private var format = "json"
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

    fun getLocations(query: String, radius : Int) : List<List<String>> {

        // query param becomes description, since we have no way to get that
        // radius param should be in miles (converted to meters for api call)
        val genURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/$format" +
                "?keyword=${query.replace(" ", "+")}" +
                "&location=$latitude%2C$longitude" +
                "&radius=${radius * 1600}" +
                "&key=$key"

        val jsonObj = JSONObject(makeRequest(genURL))

        val res = jsonObj.getJSONArray("results")
        val data: MutableList<MutableList<String>> = mutableListOf()
        for (i in 0 until res.length() - 1) {
            val itemData = getDetails(res.getJSONObject(i).getString("place_id"), query)
            data.add(itemData)
        }

        return data
    }

    private fun getDetails(placeID : String, query: String) : MutableList<String> {

        val genURL = "https://maps.googleapis.com/maps/api/place/details/$format" +
                "?placeid=$placeID" +
                "&key=$key"

        val jsonObj = JSONObject(makeRequest(genURL))
        val res = jsonObj.getJSONObject("result")

        val itemData: MutableList<String> = mutableListOf()
        itemData.add(res.getString("name"))
        itemData.add(query)
        itemData.add(
            if (res.has("formatted_phone_number")) {
                res.getString("formatted_phone_number")
            } else {
                "None"
            }
        )
        itemData.add(res.getString("formatted_address"))
        val loc = res.getJSONObject("geometry").getJSONObject("location")
        itemData.add("${String.format("%.2f", calcDistance(latitude, longitude, loc.getString("lat").toDouble(), loc.getString("lng").toDouble()))} miles")
        itemData.add(
            if (res.has("opening_hours") && res.getJSONObject("opening_hours").has("open_now")) {
                if (res.getJSONObject("opening_hours").getString("open_now") == "true") {
                    "Open"
                } else {
                    "Closed"
                }
            } else {
                "Closed"
            }
        )

        return itemData
    }

    private fun calcDistance(lat1 : Double, long1 : Double, lat2 : Double, long2 : Double) : Double {

        fun toRad (point : Double) : Double {
            return point / (180 / PI)
        }

        // Calculates distance in miles between two GPS coordinates
        val earthRadius = 3959

        val distLat = toRad(lat2 - lat1)
        val distLong = toRad(long2 - long1)

        val rLat1 = toRad(lat1)
        val rLat2 = toRad(lat2)

        val a = sin(distLat / 2) * sin(distLat / 2) +
                sin(distLong / 2) * sin(distLong / 2) * cos(rLat1) * cos(rLat2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }

    private fun makeRequest(url: String) : String {

        // needs to be async - can't do it in main thread
        val res = URL(url).readText()
        return res
    }
}
