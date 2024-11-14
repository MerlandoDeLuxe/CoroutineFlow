package com.example.coroutineflow.domain

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun loadUsers(): Flow<List<String>>

    suspend fun addUser(user: String)
}