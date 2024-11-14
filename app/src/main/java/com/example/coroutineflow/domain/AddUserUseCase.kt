package com.example.coroutineflow.domain

import javax.inject.Inject

class AddUserUseCase @Inject constructor(val repository: UserRepository) {

    operator suspend fun invoke(user: String) {
        repository.addUser(user)
    }
}