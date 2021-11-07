package com.example.hackathon2021.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hackathon2021.R

class HotlinesAdapter(private val context: Context, private val results: List<List<String>>) : RecyclerView.Adapter<HotlinesAdapter.HotlinesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotlinesViewHolder {
        //change item_result
        return HotlinesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_hotline, parent, false))
    }

    override fun onBindViewHolder(holder: HotlinesViewHolder, position: Int) {
        val result = results[position]

        // Set the item name, description, and phone number.
        holder.textName.text = result[0]
        holder.textDesc.text = result[1]
        holder.textPhone.text = result[2]
    }

    override fun getItemCount(): Int = results.size

    inner class HotlinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.text_name)
        val textDesc: TextView = itemView.findViewById(R.id.text_desc)
        val textPhone: TextView = itemView.findViewById(R.id.text_phone)
    }
}