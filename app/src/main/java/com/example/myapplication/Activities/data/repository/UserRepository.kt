package com.example.myapplication.Activities.data.repository

import com.example.myapplication.Activities.data.dao.UserDao
import com.example.myapplication.Activities.data.model.User

class UserRepository (private val dao: UserDao) {

    suspend fun insert(user : User) {
        dao.insertUser(user)
    }

    suspend fun login(email: String, password: String): User? {
        return dao.login(email, password)
    }
}