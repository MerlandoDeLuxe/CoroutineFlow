package com.example.coroutineflow.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutineflow.data.CryptoRepositoryImpl
import com.example.coroutineflow.domain.GetCurrencyListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoViewModel @Inject constructor(
    val getCurrencyListUseCase: GetCurrencyListUseCase,
    val repositoryImpl: CryptoRepositoryImpl
) : ViewModel() {

    private val loadingFlowState = MutableSharedFlow<State>()

    val state = repositoryImpl.currencyListFlow
        .filter { it.isNotEmpty() }
        .map { State.Content(currencyList = it) as State }
        .onStart { emit(State.Loading) }
        .mergeWith(loadingFlowState)
//        .shareIn(
//            scope = viewModelScope,
//            started = SharingStarted.Lazily,
//            replay = 1
//        )
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Eagerly,
//            initialValue = State.Loading
//        )

    val state2 = repositoryImpl.currencyListFlow
        .filter { it.isNotEmpty() }
        .map { State.Content(currencyList = it) as State }
        .onStart { State.Loading }
        .mergeWith(loadingFlowState)

    private fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
        return merge(this, another)
    }

    fun refreshList() {
        viewModelScope.launch {
            loadingFlowState.emit(State.Loading)
            repositoryImpl.refreshList()
        }
    }

    companion object {
        private const val TAG = "CryptoViewModel"
    }
}