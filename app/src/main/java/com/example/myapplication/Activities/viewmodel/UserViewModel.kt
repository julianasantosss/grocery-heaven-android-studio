package com.example.myapplication.Activities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Activities.data.model.User
import com.example.myapplication.Activities.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel (private val repository: UserRepository) : ViewModel(){

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun login(email: String, password: String, callback: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(email, password)
            callback(user)
        }
    }


}

class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}