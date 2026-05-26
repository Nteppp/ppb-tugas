package com.example.arsitekturmvvm.data

import com.example.arsitekturmvvm.data.local.UserDao
import com.example.arsitekturmvvm.data.local.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserRepository(
    private val userDao: UserDao
) {
    val userCount: Flow<Int> = userDao.observeUserCount()

    suspend fun seedDefaultUserIfNeeded() {
        if (userCount.first() == 0) {
            userDao.insertUser(
                UserEntity(
                    username = DEFAULT_USERNAME,
                    password = DEFAULT_PASSWORD
                )
            )
        }
    }

    suspend fun login(username: String, password: String): Boolean {
        return userDao.observeUserByCredentials(username, password).first() != null
    }

    suspend fun register(username: String, password: String): RegisterResult {
        if (userDao.getUserByUsername(username) != null) {
            return RegisterResult.UsernameAlreadyUsed
        }

        userDao.insertUser(
            UserEntity(
                username = username,
                password = password
            )
        )
        return RegisterResult.Success
    }

    companion object {
        const val DEFAULT_USERNAME = "admin"
        const val DEFAULT_PASSWORD = "password123"
    }
}

enum class RegisterResult {
    Success,
    UsernameAlreadyUsed
}
