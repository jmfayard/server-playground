package jmfayard.dev.domain

data class Book(
    val id: Long,
    val title: String,
    val isbn: String,
    val author: String,
    val genre: String?,
    val thumbnailUrl: String?,
    val description: String,
    val publisher: String
)