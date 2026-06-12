package com.example.newsapp.ui.news

import com.example.newsapp.model.Article

data class NewsUiState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null
)
