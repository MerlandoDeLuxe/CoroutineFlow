package com.example.coroutineflow.Lessons.Lesson14

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

suspend fun main() {

    val scope = CoroutineScope(Dispatchers.Default)
    val sharedFlow = MutableSharedFlow<Int>()
    val stateFlow = MutableStateFlow<Int>(0)

    val produces = scope.launch {
        delay(500)
        repeat(10) {
            println("Emitted $it")
            stateFlow.emit(it)
            println("After emit $it")
            delay(200)
        }
    }

    val consumer1 = scope.launch {
        stateFlow
            .collectLatest {
                println("Collecting $it...")
                delay(5000)
                println("Collected consumer1: $it")
            }
    }

    val consumer2 = scope.launch {
        stateFlow
            .collectLatest {
                println("Collecting $it...")
                delay(5000)
                println("Collected consumer2: $it")
            }
    }

    produces.join()
    consumer1.join()
    consumer2.join()
}