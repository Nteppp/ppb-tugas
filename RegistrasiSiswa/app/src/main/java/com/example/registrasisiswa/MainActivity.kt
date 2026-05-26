package com.example.registrasisiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.registrasisiswa.data.AppDatabase
import com.example.registrasisiswa.ui.MainScreen
import com.example.registrasisiswa.ui.theme.RegistrasiSiswaTheme
import com.example.registrasisiswa.viewmodel.StudentViewModel
import com.example.registrasisiswa.viewmodel.StudentViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        val viewModel = ViewModelProvider(
            this,
            StudentViewModelFactory(database.siswaDao())
        )[StudentViewModel::class.java]

        setContent {
            RegistrasiSiswaTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
