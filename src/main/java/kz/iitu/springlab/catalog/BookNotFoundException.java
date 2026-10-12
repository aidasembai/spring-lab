package kz.iitu.springlab.catalog; // Укажите ваш пакет, если он отличается

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}