package kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}/citation", produces = "text/plain")
    public ResponseEntity<String> getCitation(@PathVariable long id) {
        return service.findById(id)
                .map(book -> {
                    // Форматируем цитату в виде простого текста
                    String citation = String.format("%s. (%d). %s.", book.author(), book.year(), book.title());
                    return ResponseEntity.ok(citation);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}