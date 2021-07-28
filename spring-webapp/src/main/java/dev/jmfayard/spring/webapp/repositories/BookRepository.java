package dev.jmfayard.spring.webapp.repositories;

import dev.jmfayard.spring.webapp.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}
