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

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HealthFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        val view = inflater.inflate(R.layout.fragment_shelter, container, false)

        // Get healthData from MainActivity.
        val healthData: List<List<String>> = Gson().fromJson(arguments?.getString(getString(R.string.health)), object: TypeToken<List<List<String>>>() {}.type)

        // Apply the ResultAdapter to the RecyclerView.
        val resultAdapter = ResultAdapter(view.context, healthData)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler)
        recyclerView.adapter = resultAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }
}