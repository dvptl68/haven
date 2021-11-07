package com.example.hackathon2021.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.example.hackathon2021.R
import com.example.hackathon2021.utils.LocationsJSON

import com.google.android.gms.location.LocationServices
import com.google.gson.Gson

import java.lang.Exception

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    @DelicateCoroutinesApi
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Get location info.
        LocationServices.getFusedLocationProviderClient(this).lastLocation
            .addOnSuccessListener { location : Location ->
                LocationsJSON.setCoords(location.latitude, location.longitude)
            }
            .addOnFailureListener { e : Exception ->
                println("ERROR: $e\nSetting default coordinates to the Ohio Union.")
                LocationsJSON.setDefaultCoords()
            }
            .addOnCompleteListener {
                lateinit var foodData : List<List<String>>
                lateinit var healthData : List<List<String>>
                lateinit var shelterData : List<List<String>>
                lateinit var workData : List<List<String>>
                GlobalScope.launch {
                    foodData = LocationsJSON.getLocations("Food pantry", 5)
                    healthData = LocationsJSON.getLocations("Healthcare", 15)
                    shelterData = LocationsJSON.getLocations("Homeless shelter", 10)
                    workData = LocationsJSON.getLocations("Employment", 20)
                }.invokeOnCompletion {
                    // For debugging.
                    println("Data retrieval complete.")
                    println("Food: $foodData")
                    println("Health: $healthData")
                    println("Shelter: $shelterData")
                    println("Work: $workData")

                    // Prepare the intent for MainActivity.
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(getString(R.string.food), Gson().toJson(foodData))
                    intent.putExtra(getString(R.string.health), Gson().toJson(healthData))
                    intent.putExtra(getString(R.string.shelter), Gson().toJson(shelterData))
                    intent.putExtra(getString(R.string.work), Gson().toJson(workData))
                    startActivity(intent)

                    // Finish the activity to prevent the user from returning to the splash screen.
                    finish()
                }
            }
    }
}