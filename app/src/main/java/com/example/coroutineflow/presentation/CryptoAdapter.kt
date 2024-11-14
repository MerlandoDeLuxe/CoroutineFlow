package com.example.coroutineflow.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.coroutineflow.R
import com.example.coroutineflow.domain.Currency
import javax.inject.Inject

class CryptoAdapter @Inject constructor(): ListAdapter<Currency, CurrencyViewHolder>(CurrencyDiffCallBack()) {

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = getItem(position)

        with(holder) {
            textViewName.text = currency.name
            textViewPrice.text = currency.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.crypto_item_template, parent, false)

        return CurrencyViewHolder(view)
    }

}