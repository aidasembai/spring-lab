package kz.iitu.springlab.web.dto;

import kz.iitu.springlab.entity.Book;
import kz.iitu.springlab.entity.Author;
import kz.iitu.springlab.catalog.Genre;

import java.time.Instant;

public class BookMapper {

    public static BookResponse toResponse(Book book) {
        if (book == null) {
            return null;
        }

        String authorName = (book.getAuthor() != null) ? book.getAuthor().getName() : null;

        // Превращаем String из сущности в Enum Genre для BookResponse
        Genre genreEnum = null;
        if (book.getGenre() != null && !book.getGenre().isBlank()) {
            try {
                genreEnum = Genre.valueOf(book.getGenre().toUpperCase());
            } catch (IllegalArgumentException e) {
                genreEnum = null;
            }
        }

        // Конвертируем OffsetDateTime в Instant для BookResponse
        Instant createdAtInstant = (book.getCreatedAt() != null) ? book.getCreatedAt().toInstant() : null;

        return new BookResponse(
                book.getId(),
                book.getTitle(),
                authorName,
                book.getYear(),
                genreEnum,
                createdAtInstant
        );
    }

    public static Book toEntity(BookRequest request) {
        if (request == null) {
            return null;
        }
        Book book = new Book();
        book.setTitle(request.title());

        if (request.author() != null) {
            Author author = new Author();
            author.setName(request.author());
            book.setAuthor(author);
        }

        book.setYear(request.year());

        // Превращаем request.genre() (Genre) в String через .name()
        if (request.genre() != null) {
            book.setGenre(request.genre().name());
        }

        return book;
    }
}