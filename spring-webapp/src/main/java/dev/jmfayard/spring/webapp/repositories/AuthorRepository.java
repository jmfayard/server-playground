package dev.jmfayard.spring.webapp.repositories;

import dev.jmfayard.spring.webapp.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
