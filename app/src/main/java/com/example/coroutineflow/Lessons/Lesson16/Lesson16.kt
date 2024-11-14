package com.example.coroutineflow.Lessons.Lesson16

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    getFlowNumbers
        .map { State.Content(it) as State }
        .onStart { emit(State.Loading) }
        .retry(2) {
            println("Caught $it in main")
            println("Retrying connection...")
            delay(2000)
            true
        }
        .catch {
            println("Caught $it in main")
            println("Can't connect to the host")
            emit(State.Error)
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = State.Content(9)
        )
        .collect {
            when (it) {
                is State.Content -> println("Collected ${it.value}")
                State.Error -> println("Error complete operation")
                State.Loading -> println("Loading...")
            }
        }
}

val getFlowNumbers = flow {
    repeat(5) {
        emit(it)
        println("Emitted $it")
        delay(500)
    }
    throw RuntimeException("from flow block")
}

sealed class State {
    data class Content(val value: Int) : State()
    data object Loading : State()
    data object Error : State()
}