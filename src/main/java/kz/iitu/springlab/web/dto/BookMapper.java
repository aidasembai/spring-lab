package kz.iitu.springlab.web.dto;

import kz.iitu.springlab.catalog.Author;
import kz.iitu.springlab.catalog.Book;

public class BookMapper {

    public static BookResponse toResponse(Book book) {
        if (book == null) {
            return null;
        }

        String authorName = (book.getAuthor() != null) ? book.getAuthor().getName() : null;

        return new BookResponse(
                book.getId(),
                book.getTitle(),
                authorName,
                book.getYear(),
                book.getGenre(),
                book.getCreatedAt()
        );
    }

    public static Book toEntity(BookRequest request) {
        if (request == null) {
            return null;
        }
        Book book = new Book();
        book.setTitle(request.title());

        // Создаем объект Author из переданного имени (или находим/создаем при необходимости)
        if (request.author() != null) {
            Author author = new Author();
            author.setName(request.author());
            book.setAuthor(author);
        }

        book.setYear(request.year());
        book.setGenre(request.genre());
        return book;
    }
}