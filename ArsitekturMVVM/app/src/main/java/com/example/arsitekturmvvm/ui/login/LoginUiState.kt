package com.example.arsitekturmvvm.ui.login

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val message: String? = null,
    val isLoginSuccess: Boolean? = null
)
