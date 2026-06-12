package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.newsapp.ui.news.NewsScreen
import com.example.newsapp.ui.news.NewsViewModel
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NewsScreen(viewModel = viewModel)
                }
            }
        }
    }
}
