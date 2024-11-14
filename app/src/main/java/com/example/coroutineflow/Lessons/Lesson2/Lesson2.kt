package com.example.coroutineflow.Lessons.Lesson2

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

suspend fun main() {
    println("Starting...")

    getFlowByBuilderFlow().filter { it.isPrime() }
//            .filter { it > 20 }
        .map {
            println("Map")
            "Number = $it"
        }
        .collect { println(it) }
}

fun getFlowByFlowOfBuilder(): Flow<Int> {
    return flowOf(3, 4, 5, 8, 7, 9, 1, 2, 15, 17, 2, 2, 200, 478, 121, 6)
}

fun getFlowByBuilderFlow(): Flow<Int> {
    val numbers = listOf(3, 4, 5, 8, 7, 9, 1, 2, 15, 17, 2, 2, 200, 478, 121, 6)

    return flow {
        numbers.forEach { emit(it) }
        val a = 43
        emit(a)
        println("Emitted $a")
        delay(1000)
        val b = a * 10
        emit(b)
        println("Emitted $b")
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