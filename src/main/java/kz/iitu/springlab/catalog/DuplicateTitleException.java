package kz.iitu.springlab.catalog;

public class DuplicateTitleException extends RuntimeException {

    public DuplicateTitleException(String message) {
        super(message);
    }
}