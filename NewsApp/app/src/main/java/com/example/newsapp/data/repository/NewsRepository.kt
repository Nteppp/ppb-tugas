package com.example.newsapp.data.repository

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.remote.ArticleDto
import com.example.newsapp.data.remote.NewsApiService
import com.example.newsapp.data.remote.RetrofitClient
import com.example.newsapp.model.Article

class NewsRepository(
    private val apiService: NewsApiService = RetrofitClient.newsApiService,
    private val apiKey: String = BuildConfig.NEWS_API_KEY
) {
    suspend fun getTopHeadlines(): Result<List<Article>> = runCatching {
        require(apiKey.isNotBlank()) {
            "NEWS_API_KEY belum diisi. Tambahkan NEWS_API_KEY=api_key_anda ke local.properties."
        }

        apiService.getTopHeadlines(apiKey = apiKey)
            .articles
            .orEmpty()
            .mapNotNull { it.toArticleOrNull() }
    }

    suspend fun searchNews(query: String): Result<List<Article>> = runCatching {
        require(apiKey.isNotBlank()) {
            "NEWS_API_KEY belum diisi. Tambahkan NEWS_API_KEY=api_key_anda ke local.properties."
        }

        apiService.searchNews(query = query, apiKey = apiKey)
            .articles
            .orEmpty()
            .mapNotNull { it.toArticleOrNull() }
    }

    private fun ArticleDto.toArticleOrNull(): Article? {
        val safeTitle = title?.takeUnless { it.isBlank() || it == "[Removed]" } ?: return null
        val safeUrl = url?.takeUnless { it.isBlank() } ?: return null

        return Article(
            title = safeTitle,
            author = author?.takeUnless { it.isBlank() } ?: "Unknown author",
            description = description?.takeUnless { it.isBlank() } ?: "No description available.",
            imageUrl = urlToImage?.takeUnless { it.isBlank() },
            sourceName = source?.name?.takeUnless { it.isBlank() } ?: "Unknown source",
            publishedAt = publishedAt.orEmpty(),
            url = safeUrl
        )
    }
}
