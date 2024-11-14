package com.example.coroutineflow.data

import com.example.coroutineflow.di.ApplicationScope
import com.example.coroutineflow.domain.CryptoRepository
import com.example.coroutineflow.domain.Currency
import com.example.coroutineflow.presentation.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.random.Random

class CryptoRepositoryImpl @Inject constructor() : CryptoRepository {
    private val TAG = "CryptoRepositoryImpl"

    private val currencyNames = listOf("BTC", "ETH", "USDT", "BNB", "USDC")
    private val currencyList = mutableListOf<Currency>()

    private val refreshEvents = MutableSharedFlow<Unit>()

    private val scope = CoroutineScope(Dispatchers.Default)

    override val currencyListFlow: Flow<List<Currency>> = flow {
        delay(3000)
        generateCurrencyList()
        emit(currencyList.toList())
        refreshEvents.collect {
            delay(3000)
            generateCurrencyList()
            emit(currencyList.toList())
        }
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = currencyList.toList()
    )

    suspend fun refreshList() {
        refreshEvents.emit(Unit)
    }

    private fun generateCurrencyList() {
        val prices = buildList {
            repeat(currencyNames.size) {
                add(Random.nextInt(1000, 2000))
            }
        }
        val newData = buildList {
            for ((index, currencyName) in currencyNames.withIndex()) {
                val price = prices[index]
                val currency = Currency(name = currencyName, price = price)
                add(currency)
            }
        }
        currencyList.clear()
        currencyList.addAll(newData)
    }
}