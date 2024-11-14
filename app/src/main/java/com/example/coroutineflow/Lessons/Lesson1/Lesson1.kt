package com.example.coroutineflow.Lessons.Lesson1

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

suspend fun main() {
    println("Starting...")

    val numbers = listOf(3, 4, 5, 8, 7, 9, 1, 2, 15, 17, 2, 2, 200, 478, 121, 6).asFlow()
    numbers
        .filter { it.isPrime() }
//            .filter { it > 20 }
        .map {
            println("Map")
            "Number = $it"
        }
        .collect{ println(it) }
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