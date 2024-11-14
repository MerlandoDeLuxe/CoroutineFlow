package com.example.coroutineflow.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyListUseCase @Inject constructor(val repository: CryptoRepository) {

    operator fun invoke(): Flow<List<Currency>> {
        return repository.currencyListFlow
    }
}