package com.example.coroutineflow.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.coroutineflow.domain.Currency
import javax.inject.Inject

class CurrencyDiffCallBack @Inject constructor() : DiffUtil.ItemCallback<Currency>() {

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.price == newItem.price
    }
}