package com.example.coroutineflow.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.coroutineflow.R
import javax.inject.Inject

class CurrencyViewHolder @Inject constructor(itemView: View) : ViewHolder(itemView) {

    val textViewName = itemView.findViewById<TextView>(R.id.textViewName)
    val textViewPrice = itemView.findViewById<TextView>(R.id.textViewPrice)
}