package com.example.coroutineflow.data

import com.example.coroutineflow.domain.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {

    private val users = mutableListOf("Nick", "John", "Max")

    override suspend fun addUser(user: String) {
        delay(10)
        users.add(user)
    }

    override suspend fun loadUsers(): Flow<List<String>> = flow {
        while (true) {
            emit(users.toList())
            delay(500)
        }
    }
}