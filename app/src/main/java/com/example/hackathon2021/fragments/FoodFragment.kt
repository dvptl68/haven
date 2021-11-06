package com.example.hackathon2021.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hackathon2021.R
import com.example.hackathon2021.adapters.ResultAdapter

class FoodFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        val view = inflater.inflate(R.layout.fragment_food, container, false)

        // Dummy data.
        val dummyData = listOf(
            listOf("Star House", "Drop-in center, food, clothing, mental health therapy, healthca" +
                    "re, laundry, mailbox, computer lab, workforce, educational and enrichment op" +
                    "portunities, and more", "(614) 826-5868", "123 First St", "1.1 miles away", "Open until 10:0" +
                    "0 PM"),
            listOf("Stonewall Columbus", "Advocacy and support", "(614) 299-7764", "432 One Ave", "0.8 miles away",
                    "Closed until Monday at 10:00 AM"),
            listOf("Dress for Success", "Professional attire", "(614) 291-5420", "980 Dev St", "0.8 miles away",
                    "Closed until Monday at 8:30 AM"),
            listOf("Huckleberry House", "Crisis shelter, hot meals, clothing, counseling, life sk" +
                    "ills, and more", "(614) 294-5553", "625 Ben Ave", "1.4 miles away", "Closed until Monday a" +
                    "t 12:00 PM")
        )

        // Apply the ResultAdapter to the RecyclerView.
        val resultAdapter = ResultAdapter(view.context, dummyData)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.adapter = resultAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }
}