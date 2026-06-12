package com.example.newsapp.model

data class Article(
    val title: String,
    val author: String,
    val description: String,
    val imageUrl: String?,
    val sourceName: String,
    val publishedAt: String,
    val url: String
)
