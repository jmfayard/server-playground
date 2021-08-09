package jmfayard.dev.openlibrary.domain

interface DaoRepository {
    fun findAllBooks(): List<Book>
}

