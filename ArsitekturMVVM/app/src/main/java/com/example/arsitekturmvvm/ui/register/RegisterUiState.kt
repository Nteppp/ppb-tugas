package com.example.arsitekturmvvm.ui.register

data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val message: String? = null,
    val isRegisterSuccess: Boolean? = null
)
