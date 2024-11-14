package com.example.coroutineflow.presentation

import com.example.coroutineflow.domain.Currency

sealed class State {
    object Initial : State()
    object Loading : State()
    data class Content(val currencyList: List<Currency>) : State()
}

