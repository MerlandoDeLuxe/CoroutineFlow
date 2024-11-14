package com.example.coroutineflow.domain

import javax.inject.Inject

data class Currency @Inject constructor(
    val name: String,
    val price: Int
) {
}