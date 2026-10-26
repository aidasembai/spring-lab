package kz.iitu.springlab.service;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookRepository;
import kz.iitu.springlab.catalog.BookNotFoundException;
import kz.iitu.springlab.catalog.DuplicateTitleException;
import kz.iitu.springlab.catalog.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class BookService {

    private final BookRepository repository;
    // Хранилище для реализации идемпотентности (ключ -> созданная книга)
    private final Map<String, Book> idempotencyStore = new ConcurrentHashMap<>();

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    // --- Обновленный метод с пагинацией и фильтрацией по жанру ---
    public Page<Book> find(Genre genre, Pageable pageable) {
        if (genre != null) {
            return repository.findByGenre(genre, pageable);
        }
        return repository.findAll(pageable);
    }

    // Старый метод findAll для обратной совместимости (если используется где-то еще)
    public List<Book> findAll(String author) {
        List<Book> all = repository.findAll();
        if (author == null || author.isBlank()) {
            return all;
        }
        return all.stream()
                .filter(b -> b.getAuthor() != null
                        && b.getAuthor().getName() != null
                        && b.getAuthor().getName().toLowerCase().contains(author.toLowerCase()))
                .toList();
    }

    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    // Перегруженный метод создания с поддержкой ключа идемпотентности
    public Book create(Book book, String idempotencyKey) {
        // Если передан ключ идемпотентности и он уже есть в хранилище, возвращаем ранее созданный результат
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            if (idempotencyStore.containsKey(idempotencyKey)) {
                return idempotencyStore.get(idempotencyKey);
            }
        }

        // Проверка на дубликат по названию и автору
        boolean duplicateExists = repository.findAll().stream()
                .anyMatch(b -> b.getTitle() != null
                        && b.getAuthor() != null
                        && b.getAuthor().getName() != null
                        && book.getAuthor() != null
                        && book.getAuthor().getName() != null
                        && b.getTitle().equalsIgnoreCase(book.getTitle())
                        && b.getAuthor().getName().equalsIgnoreCase(book.getAuthor().getName()));

        if (duplicateExists) {
            throw new DuplicateTitleException("Book with title '" + book.getTitle() + "' already exists");
        }

        Book savedBook = repository.save(book);

        // Сохраняем результат в кеш идемпотентности
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            idempotencyStore.put(idempotencyKey, savedBook);
        }

        return savedBook;
    }

    // Обратная совместимость для вызовов без ключа
    public Book create(Book book) {
        return create(book, null);
    }

    public Book update(long id, Book book) {
        Book existingBook = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));

        // Обновляем поля существующей сущности через сеттеры
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setYear(book.getYear());
        existingBook.setGenre(book.getGenre());

        return repository.save(existingBook);
    }

    public void delete(long id) {
        if (repository.findById(id).isEmpty()) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}