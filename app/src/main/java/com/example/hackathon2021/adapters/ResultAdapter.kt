package com.example.hackathon2021.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hackathon2021.R

class ResultAdapter(private val context: Context, private val results: List<List<String>>) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false))
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = results[position]

        // Set the item description, email, location, and phone number.
        holder.textDescription.text = result[1]
        holder.textAddress.text = result[3]
        holder.textDistance.text = result[4]
        holder.textLocation.text = result[0]
        holder.textPhoneNumber.text = result[2]

        // Set the item hours and corresponding color.
        holder.textHours.text = result[5]
        if (result[5][0] == 'O')
            holder.textHours.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
        else
            holder.textHours.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
    }

    override fun getItemCount(): Int = results.size

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textDescription: TextView = itemView.findViewById(R.id.text_description)
        val textAddress: TextView = itemView.findViewById(R.id.text_address)
        val textDistance: TextView = itemView.findViewById(R.id.text_distance)
        val textHours: TextView = itemView.findViewById(R.id.text_hours)
        val textLocation: TextView = itemView.findViewById(R.id.text_location)
        val textPhoneNumber: TextView = itemView.findViewById(R.id.text_phone_number)
    }
}