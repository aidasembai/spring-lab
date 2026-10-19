package kz.iitu.springlab.web.dto;

import kz.iitu.springlab.catalog.Book;

public class BookMapper {

    public static BookResponse toResponse(Book book) {
        if (book == null) {
            return null;
        }
        return new BookResponse(
                book.id(),
                book.title(),
                book.author(),
                book.year(),
                book.genre(),
                book.createdAt()
        );
    }

    public static Book toEntity(BookRequest request) {
        if (request == null) {
            return null;
        }
        return new Book(
                null, // или id, если передается
                request.title(),
                request.author(),
                request.year(),
                request.genre(),
                null  // или время создания, если задается автоматически
        );
    }
}