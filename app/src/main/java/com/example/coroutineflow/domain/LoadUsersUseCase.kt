package com.example.coroutineflow.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadUsersUseCase @Inject constructor(val repository: UserRepository) {

    operator suspend fun invoke(): Flow<List<String>> {
        return repository.loadUsers()
    }
}