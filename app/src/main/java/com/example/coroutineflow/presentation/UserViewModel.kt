package com.example.coroutineflow.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutineflow.domain.AddUserUseCase
import com.example.coroutineflow.domain.LoadUsersUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val loadUsersUseCase: LoadUsersUseCase
) : ViewModel() {

    private val _users = MutableLiveData<List<String>>()
    val users: LiveData<List<String>>
        get() = _users

    private val _state = MutableLiveData<State>()
    val state: MutableLiveData<State>
        get() = _state

    init {
        loadUsers()
    }

    fun addUser(user: String) {
        viewModelScope.launch {
            addUserUseCase(user)
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            loadUsersUseCase()
                .collect {
                    _users.value = it
                }
        }
    }
}