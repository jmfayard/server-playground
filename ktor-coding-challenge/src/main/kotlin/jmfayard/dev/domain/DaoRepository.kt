package jmfayard.dev.domain

interface DaoRepository {
    fun findAllBooks(): List<Book>
}

