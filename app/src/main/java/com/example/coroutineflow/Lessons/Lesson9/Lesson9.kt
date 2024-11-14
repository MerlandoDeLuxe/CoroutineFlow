package com.example.coroutineflow.Lessons.Lesson9

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

val coroutineScope = CoroutineScope(Dispatchers.IO)

suspend fun main() {
    val flow = MutableSharedFlow<Int>()
//    val flow = getFlow()

    coroutineScope.launch {
        repeat(5) {
            flow.emit(it)
            println("Emitted $it")
            delay(1000)
        }
    }

    val job1 = coroutineScope.launch {
        flow.collect {
            println("Coroutine $this got $it from 1st collector")
        }
    }

    delay(5000)
    val job2 = coroutineScope.launch {
        flow.collect {
            println("Coroutine $this got $it from 1st collector")
        }
    }
    job1.join()
    job2.join()
}

fun getFlow(): Flow<Int> {

    return flow {
        repeat(5) {
            println("Emitted: $it")
            emit(it)
            delay(1000)
        }
    }
}