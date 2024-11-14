package com.example.coroutineflow.domain

import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    val currencyListFlow: Flow<List<Currency>>
}