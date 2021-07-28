package dev.jmfayard.spring.webapp.bootstrap;

import dev.jmfayard.spring.webapp.domain.Author;
import dev.jmfayard.spring.webapp.domain.Book;
import dev.jmfayard.spring.webapp.repositories.AuthorRepository;
import dev.jmfayard.spring.webapp.repositories.BookRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BootstrapData(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        var ddd = new Book("Domain Driven Design", "123456");
        var evans = new Author("Eric", "Evans");
        ddd.getAuthors().add(evans);

        System.out.println("Boostraping data start");
        authorRepository.save(evans);
        bookRepository.save(ddd);
        System.out.println("Bootstrapping data done - " + bookRepository.count() + " books");
    }
}
