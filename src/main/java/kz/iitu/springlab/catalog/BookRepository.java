package kz.iitu.springlab.catalog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // --- 3.1. Derived queries ---
    List<Book> findByGenre(Genre genre);

    List<Book> findByTitleContainingIgnoreCase(String fragment);

    List<Book> findByYearBetweenOrderByYearDesc(int from, int to);

    List<Book> findTop3ByGenreOrderByYearDesc(Genre genre);

    long countByAuthorName(String name);        // a nested property

    // --- 3.2. JPQL Query with named parameters ---
    @Query("""
            select b from Book b
            where b.genre = :genre and b.year >= :year
            order by b.title""")
    List<Book> search(@Param("genre") Genre genre, @Param("year") int year);

    // --- 3.2. Bulk update query ---
    @Modifying(clearAutomatically = true)
    @Query("update Book b set b.archived = true where b.year < :year")
    int archiveOlderThan(@Param("year") int year);      // @Transactional on the caller

    // --- 3.4. N+1 Problem Fix (Join Fetch) ---
    @Query("select b from Book b join fetch b.author")
    List<Book> findAllWithAuthor();

    // --- 4.1. Pagination and Sorting ---
    @EntityGraph(attributePaths = "author")
    Page<Book> findByGenre(Genre genre, Pageable pageable);

    @EntityGraph(attributePaths = "author")
    Page<Book> findAll(Pageable pageable);
}