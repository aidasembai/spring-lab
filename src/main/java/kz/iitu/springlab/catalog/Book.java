package kz.iitu.springlab.catalog;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(name = "\"year\"")
    private int year;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Genre genre;

    private boolean archived;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Version
    private long version;

    // --- Новое: Связь Many-to-One с Author ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    // --- Новое: Связь Many-to-Many с Tag через таблицу book_tag ---
    @ManyToMany
    @JoinTable(name = "book_tag",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    public Book() { }

    public Book(String title, Author author, int year, Genre genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
    }

    public Book(Long id, String title, Author author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public long getVersion() {
        return version;
    }
}