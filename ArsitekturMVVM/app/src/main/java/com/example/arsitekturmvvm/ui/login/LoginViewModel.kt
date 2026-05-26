package com.example.arsitekturmvvm.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arsitekturmvvm.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.seedDefaultUserIfNeeded()
        }
    }

    fun onUsernameChange(username: String) {
        _uiState.update {
            it.copy(
                username = username,
                message = null,
                isLoginSuccess = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                message = null,
                isLoginSuccess = null
            )
        }
    }

    fun login() {
        val username = uiState.value.username.trim()
        val password = uiState.value.password

        if (username.isEmpty() || password.isEmpty()) {
            _uiState.update {
                it.copy(
                    message = "Username dan password wajib diisi.",
                    isLoginSuccess = false
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = null) }

            val isValidUser = userRepository.login(username, password)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    message = if (isValidUser) {
                        "Login berhasil. Selamat datang, $username!"
                    } else {
                        "Login gagal. Username atau password salah."
                    },
                    isLoginSuccess = isValidUser
                )
            }
        }
    }
}
