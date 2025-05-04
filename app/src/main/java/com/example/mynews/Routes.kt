package com.example.mynews

import kotlinx.serialization.Serializable

@Serializable
object HomePageScreen
@Serializable
data class NewsArticlePageScreen(
    val url: String   // we have to mention all arguments which is going to passed in NewsArticlePage, by making object to data class
)