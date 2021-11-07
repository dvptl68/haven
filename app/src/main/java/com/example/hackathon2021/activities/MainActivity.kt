package com.example.hackathon2021.activities

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.MenuItem

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

import com.example.hackathon2021.R
import com.example.hackathon2021.fragments.*

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import com.google.android.gms.location.LocationServices
import com.example.hackathon2021.util.LocationsJSON
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var drawer: DrawerLayout

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the toolbar.
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Initialize the navigation drawer and its listener.
        drawer = findViewById(R.id.drawer)
        val drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // Initialize the TabLayout, ViewPager, and the adapter.
        val tabLayout = findViewById<TabLayout>(R.id.tab)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val viewPagerAdapter = ViewPagerAdapter(this)

        // Populate the adapter.
        viewPagerAdapter.addFragment(FoodFragment(), getString(R.string.food))
        viewPagerAdapter.addFragment(ShelterFragment(), getString(R.string.shelter))
        viewPagerAdapter.addFragment(HealthFragment(), getString(R.string.health))
        viewPagerAdapter.addFragment(HotlinesFragment(), getString(R.string.hotlines))
        viewPagerAdapter.addFragment(WorkFragment(), getString(R.string.work))

        // Set the adapter.
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = viewPagerAdapter.getTitle(position)
        }.attach()

        // Get location info
        LocationServices.getFusedLocationProviderClient(this).lastLocation
            .addOnSuccessListener { location : Location ->
                LocationsJSON.setCoords(location.latitude, location.longitude)
            }
            .addOnFailureListener() { e : Exception ->
                println("ERROR: $e\nSetting default coordinates to the Ohio Union")
                LocationsJSON.setDefaultCoords()
            }
            .addOnCompleteListener {
                lateinit var foodData : List<List<String>>
                lateinit var healthData : List<List<String>>
                lateinit var shelterData : List<List<String>>
                lateinit var workData : List<List<String>>
                GlobalScope.launch {
                    foodData = LocationsJSON.getLocations("Food Pantry", 5)
                    healthData = LocationsJSON.getLocations("Health Care", 15)
                    shelterData = LocationsJSON.getLocations("Shelter", 10)
                    workData = LocationsJSON.getLocations("Employment", 20)
                }.invokeOnCompletion {
                    println("Data retrieval complete")
                    println("food: $foodData")
                    println("health: $healthData")
                    println("shelter: $shelterData")
                    println("work: $workData")
                    // somehow update the UI elements with the received data
                }
            }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Stores the fragments within the activity.
     *
     * @author Dimitri Joy
     */
    internal class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        private val fragments: ArrayList<Fragment> = ArrayList()
        private val titles: ArrayList<String> = ArrayList()

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        /**
         * Adds a fragment to the adapter.
         *
         * @param fragment the fragment
         * @param title the title
         */
        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }

        /**
         * Gets the title of a fragment.
         *
         * @param position the index
         * @return the title
         */
        fun getTitle(position: Int): String {
            return titles[position]
        }
    }
}