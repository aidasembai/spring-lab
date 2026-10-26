package kz.iitu.springlab.web;

import jakarta.validation.constraints.*;
import kz.iitu.springlab.catalog.Genre;
import kz.iitu.springlab.config.NoDigits;

import java.util.List;

public class BookForm {

    @NotBlank(message = "{book.title.required}")
    @Size(max = 120, message = "{book.title.size}")
    private String title;

    @NotBlank(message = "{book.author.required}")
    @NoDigits
    private String author;

    @NotNull(message = "{book.year.range}")
    @Min(value = 1450, message = "{book.year.range}")
    @Max(value = 2100, message = "{book.year.range}")
    private Integer year;

    @NotNull(message = "{book.genre.required}")
    private Genre genre;

    private boolean available;

    // Новое поле для задания №6
    @NotEmpty(message = "{book.tags.required}")
    private List<String> tags;

    // Геттеры и сеттеры
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    // Геттер и сеттер для tags
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}