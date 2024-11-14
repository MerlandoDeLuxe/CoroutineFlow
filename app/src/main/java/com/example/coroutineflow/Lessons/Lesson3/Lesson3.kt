package com.example.coroutineflow.Lessons.Lesson3

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList

suspend fun main() {
    println("Starting...")

    val result = getFlowByBuilderFlow().filter { it.isPrime() }
//            .filter { it > 20 }
        .map {
            println("Map")
            "Number = $it"
        }
        .first()
    println(result)
}

fun getFlowByFlowOfBuilder(): Flow<Int> {
    return flowOf(3, 4, 5, 8, 7, 9, 1, 2, 15, 17, 2, 2, 200, 478, 121, 6)
}

fun getFlowByBuilderFlow(): Flow<Int> {
    val firstFlow = getFlowByFlowOfBuilder()

    return flow {
//        firstFlow.collect {
//            println("Emitted from firstFlow: $it")
//            emit(it)
//        }
        emitAll(firstFlow)
    }
}

suspend fun Int.isPrime(): Boolean {
    if (this <= 1) return false
    for (i in 2..this / 2) {
        delay(50)
//        delay(50)
        if (this % i == 0) return false
    }
    return true
}