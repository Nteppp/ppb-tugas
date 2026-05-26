package com.example.arsitekturmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import com.example.arsitekturmvvm.data.UserRepository
import com.example.arsitekturmvvm.data.local.AppDatabase
import com.example.arsitekturmvvm.ui.login.LoginRoute
import com.example.arsitekturmvvm.ui.login.LoginViewModel
import com.example.arsitekturmvvm.ui.login.LoginViewModelFactory
import com.example.arsitekturmvvm.ui.register.RegisterRoute
import com.example.arsitekturmvvm.ui.register.RegisterViewModel
import com.example.arsitekturmvvm.ui.register.RegisterViewModelFactory
import com.example.arsitekturmvvm.ui.theme.ArsitekturMVVMTheme

private enum class AuthScreen {
    Login,
    Register
}

class MainActivity : ComponentActivity() {
    private val database by lazy {
        AppDatabase.getDatabase(applicationContext)
    }

    private val userRepository by lazy {
        UserRepository(database.userDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(userRepository)
        )[LoginViewModel::class.java]

        val registerViewModel = ViewModelProvider(
            this,
            RegisterViewModelFactory(userRepository)
        )[RegisterViewModel::class.java]

        setContent {
            ArsitekturMVVMTheme {
                var currentScreen by remember {
                    mutableStateOf(AuthScreen.Login)
                }

                when (currentScreen) {
                    AuthScreen.Login -> LoginRoute(
                        viewModel = loginViewModel,
                        onRegisterClick = {
                            currentScreen = AuthScreen.Register
                        }
                    )

                    AuthScreen.Register -> RegisterRoute(
                        viewModel = registerViewModel,
                        onLoginClick = {
                            currentScreen = AuthScreen.Login
                        }
                    )
                }
            }
        }
    }
}
