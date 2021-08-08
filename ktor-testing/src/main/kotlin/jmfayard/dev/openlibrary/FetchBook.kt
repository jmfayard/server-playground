package jmfayard.dev.openlibrary


import io.ktor.client.*
import io.ktor.client.request.*
import jmfayard.dev.domain.Book

suspend fun fetchBookInfos(
    isbn: String,
    httpClient: HttpClient = client,
    baseUrl: String = "https://openlibrary.org"
): Book {
    // GET https://openlibrary.org/isbn/0873386035.json
    val book = httpClient.get<OLBook>("$baseUrl/isbn/${isbn.trim()}.json")
    val cover = book.covers.firstOrNull()?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }

    // GET https://openlibrary.org/authors/OL222379A.json
    val authors = book.authors.map { ref ->
        httpClient.get<OLAuthor>("$baseUrl${ref.key}.json")
    }.joinToString { author -> author.name }

    return Book(
        id = -1,
        title = book.title,
        isbn = book.isbn_10.first(),
        author = authors,
        genre = null,
        thumbnailUrl = cover,
        description = book.subtitle,
        publisher = book.publishers.joinToString(" - ")
    )
}