package com.example.arsitekturmvvm.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arsitekturmvvm.data.RegisterResult
import com.example.arsitekturmvvm.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onUsernameChange(username: String) {
        _uiState.update {
            it.copy(
                username = username,
                message = null,
                isRegisterSuccess = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                message = null,
                isRegisterSuccess = null
            )
        }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update {
            it.copy(
                confirmPassword = confirmPassword,
                message = null,
                isRegisterSuccess = null
            )
        }
    }

    fun register() {
        val username = uiState.value.username.trim()
        val password = uiState.value.password
        val confirmPassword = uiState.value.confirmPassword

        val validationMessage = validateInput(username, password, confirmPassword)
        if (validationMessage != null) {
            _uiState.update {
                it.copy(
                    message = validationMessage,
                    isRegisterSuccess = false
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = null) }

            val result = userRepository.register(username, password)
            _uiState.update {
                when (result) {
                    RegisterResult.Success -> it.copy(
                        username = "",
                        password = "",
                        confirmPassword = "",
                        isLoading = false,
                        message = "Registrasi berhasil. Silakan login.",
                        isRegisterSuccess = true
                    )

                    RegisterResult.UsernameAlreadyUsed -> it.copy(
                        isLoading = false,
                        message = "Username sudah digunakan.",
                        isRegisterSuccess = false
                    )
                }
            }
        }
    }

    private fun validateInput(
        username: String,
        password: String,
        confirmPassword: String
    ): String? {
        return when {
            username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ->
                "Semua field wajib diisi."

            password.length < 6 ->
                "Password minimal 6 karakter."

            password != confirmPassword ->
                "Konfirmasi password tidak sama."

            else -> null
        }
    }
}
