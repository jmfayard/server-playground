package jmfayard.dev.openlibrary

import kotlinx.serialization.Serializable

// OL stands for Open Library

@Serializable
data class OLAuthor(

    val name: String,

    val key: String,

    val birth_date: String,

    val id: Int,

    val revision: Int

)

@Serializable
data class OLAuthorRef(

    val key: String

)

@Serializable
data class OLBook(

    val publishers: List<String>,

    val number_of_pages: Int,

    val isbn_10: List<String>,

    val covers: List<Int>,

    val key: String,

    val authors: List<OLAuthorRef>,

    val title: String,

    val subtitle: String,

    val notes: String,

    val subjects: List<String>

)