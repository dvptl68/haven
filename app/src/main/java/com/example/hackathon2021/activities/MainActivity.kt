package com.example.hackathon2021.activities

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

import com.example.hackathon2021.fragments.*
import com.example.hackathon2021.R

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private lateinit var drawer: DrawerLayout

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

        // Prepare the food fragment.
        val foodData: List<List<String>> = Gson().fromJson(intent.getStringExtra(getString(R.string.food)), object: TypeToken<List<List<String>>>() {}.type)
        val foodFragment = FoodFragment()
        val foodBundle = Bundle()
        foodBundle.putString(getString(R.string.food), Gson().toJson(foodData))
        foodFragment.arguments = foodBundle

        // Prepare the shelter fragment.
        val shelterData: List<List<String>> = Gson().fromJson(intent.getStringExtra(getString(R.string.shelter)), object: TypeToken<List<List<String>>>() {}.type)
        val shelterFragment = ShelterFragment()
        val shelterBundle = Bundle()
        shelterBundle.putString(getString(R.string.shelter), Gson().toJson(shelterData))
        shelterFragment.arguments = shelterBundle

        // Prepare the health fragment.
        val healthData: List<List<String>> = Gson().fromJson(intent.getStringExtra(getString(R.string.health)), object: TypeToken<List<List<String>>>() {}.type)
        val healthFragment = HealthFragment()
        val healthBundle = Bundle()
        healthBundle.putString(getString(R.string.health), Gson().toJson(healthData))
        healthFragment.arguments = healthBundle

        // Prepare the work fragment.
        val workData: List<List<String>> = Gson().fromJson(intent.getStringExtra(getString(R.string.work)), object: TypeToken<List<List<String>>>() {}.type)
        val workFragment = WorkFragment()
        val workBundle = Bundle()
        workBundle.putString(getString(R.string.work), Gson().toJson(workData))
        workFragment.arguments = workBundle

        // Populate the adapter.
        viewPagerAdapter.addFragment(foodFragment, getString(R.string.food))
        viewPagerAdapter.addFragment(shelterFragment, getString(R.string.shelter))
        viewPagerAdapter.addFragment(healthFragment, getString(R.string.health))
        viewPagerAdapter.addFragment(HotlinesFragment(), getString(R.string.hotlines))
        viewPagerAdapter.addFragment(workFragment, getString(R.string.work))

        // Set the adapter.
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = viewPagerAdapter.getTitle(position)
        }.attach()
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