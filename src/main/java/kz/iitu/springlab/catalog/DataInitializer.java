package kz.iitu.springlab;

import kz.iitu.springlab.catalog.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(BookRepository bookRepository, AuthorRepository authorRepository, TagRepository tagRepository) {
        return args -> {
            // Если данные уже есть, не дублируем их при перезапуске
            if (!bookRepository.findAll().isEmpty()) {
                return;
            }

            // 1. Создаем 4 тега
            Tag tag1 = tagRepository.save(new Tag("Bestseller"));
            Tag tag2 = tagRepository.save(new Tag("Classic"));
            Tag tag3 = tagRepository.save(new Tag("Sci-Fi"));
            Tag tag4 = tagRepository.save(new Tag("Award Winning"));

            // 2. Создаем 3 авторов
            Author author1 = new Author("J.K. Rowling", "UK");
            Author author2 = new Author("George Orwell", "UK");
            Author author3 = new Author("Isaac Asimov", "USA");

            authorRepository.saveAll(List.of(author1, author2, author3));

            // 3. Создаем 8 книг
            Book book1 = new Book();
            book1.setTitle("Harry Potter and the Sorcerer's Stone");
            book1.setYear(1997);
            book1.setGenre(Genre.FANTASY);
            book1.setAuthor(author1);
            book1.setTags(Set.of(tag1, tag4));

            Book book2 = new Book();
            book2.setTitle("Harry Potter and the Chamber of Secrets");
            book2.setYear(1998);
            book2.setGenre(Genre.FANTASY);
            book2.setAuthor(author1);
            book2.setTags(Set.of(tag1));

            Book book3 = new Book();
            book3.setTitle("Harry Potter and the Prisoner of Azkaban");
            book3.setYear(1999);
            book3.setGenre(Genre.FANTASY);
            book3.setAuthor(author1);
            book3.setTags(Set.of(tag2));

            Book book4 = new Book();
            book4.setTitle("1984");
            book4.setYear(1949);
            book4.setGenre(Genre.FICTION);
            book4.setAuthor(author2);
            book4.setTags(Set.of(tag2, tag3));

            Book book5 = new Book();
            book5.setTitle("Animal Farm");
            book5.setYear(1945);
            book5.setGenre(Genre.FICTION);
            book5.setAuthor(author2);
            book5.setTags(Set.of(tag2));

            Book book6 = new Book();
            book6.setTitle("Foundation");
            book6.setYear(1951);
            book6.setGenre(Genre.SCIENCE);
            book6.setAuthor(author3);
            book6.setTags(Set.of(tag3));

            Book book7 = new Book();
            book7.setTitle("I, Robot");
            book7.setYear(1950);
            book7.setGenre(Genre.SCIENCE);
            book7.setAuthor(author3);
            book7.setTags(Set.of(tag3));

            Book book8 = new Book();
            book8.setTitle("The Caves of Steel");
            book8.setYear(1954);
            book8.setGenre(Genre.SCIENCE);
            book8.setAuthor(author3);
            book8.setTags(Set.of(tag1));

            bookRepository.saveAll(List.of(book1, book2, book3, book4, book5, book6, book7, book8));
        };
    }
}