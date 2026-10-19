package kz.iitu.springlab.service;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookRepository;
import kz.iitu.springlab.catalog.BookNotFoundException;
import kz.iitu.springlab.catalog.DuplicateTitleException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll(String author) {
        List<Book> all = repository.findAll();
        if (author == null || author.isBlank()) {
            return all;
        }
        return all.stream()
                .filter(b -> b.author().toLowerCase().contains(author.toLowerCase()))
                .toList();
    }

    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    public Book create(Book book) {
        boolean duplicateExists = repository.findAll().stream()
                .anyMatch(b -> b.title().equalsIgnoreCase(book.title())
                        && b.author().equalsIgnoreCase(book.author()));
        if (duplicateExists) {
            throw new DuplicateTitleException("Book with title '" + book.title() + "' already exists");
        }
        return repository.save(book);
    }

    public Book update(long id, Book book) {
        if (repository.findById(id).isEmpty()) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        Book updatedBook = new Book(
                id,
                book.title(),
                book.author(),
                book.year(),
                book.genre(),
                book.createdAt()
        );
        return repository.save(updatedBook);
    }

    public void delete(long id) {
        if (repository.findById(id).isEmpty()) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}