package com.example.newsapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: NewsRepository = NewsRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewsUiState(isLoading = true))
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null

    init {
        loadTopHeadlines()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun loadTopHeadlines() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null, searchQuery = "")
            }

            repository.getTopHeadlines()
                .onSuccess { articles ->
                    _uiState.update {
                        it.copy(isLoading = false, articles = articles, errorMessage = null)
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = throwable.message)
                    }
                }
        }
    }

    fun searchNews() {
        val query = uiState.value.searchQuery.trim()
        if (query.isBlank()) {
            loadTopHeadlines()
            return
        }

        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            repository.searchNews(query)
                .onSuccess { articles ->
                    _uiState.update {
                        it.copy(isLoading = false, articles = articles, errorMessage = null)
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = throwable.message)
                    }
                }
        }
    }
}
