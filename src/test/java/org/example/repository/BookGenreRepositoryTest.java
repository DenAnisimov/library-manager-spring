package org.example.repository;

import org.example.config.TestConfig;
import org.example.entity.Book;
import org.example.entity.BookGenre;
import org.example.entity.Genre;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Tag("DockerRequired")
class BookGenreRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:latest").withDatabaseName("library_manager");

    private BookGenreRepository bookGenreRepository;
    private BookRepository bookRepository;
    private GenreRepository genreRepository;

    @BeforeAll
    static void setUp() {
        container.start();
    }

    @AfterAll
    static void tearDown() {
        container.stop();
    }

    @BeforeEach
    void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        bookGenreRepository = context.getBean(BookGenreRepository.class);
        bookRepository = context.getBean(BookRepository.class);
        genreRepository = context.getBean(GenreRepository.class);

        // Clean up
        bookGenreRepository.deleteAll();
        bookRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    void testSaveAndFindBookGenre() {
        Book book = new Book("Test Book", "Test Description", null);
        Genre genre = new Genre("Science Fiction");
        bookRepository.save(book);
        genreRepository.save(genre);

        BookGenre bookGenre = new BookGenre(book, genre);
        BookGenre savedBookGenre = bookGenreRepository.save(bookGenre);
        BookGenre foundBookGenre = bookGenreRepository.findById(savedBookGenre.getId()).orElse(null);

        assertNotNull(foundBookGenre);
        assertEquals(book.getId(), foundBookGenre.getBook().getId());
        assertEquals(genre.getId(), foundBookGenre.getGenre().getId());
    }

    @Test
    void testUpdateBookGenre() {
        Book book = new Book("Test Book", "Test Description", null);
        Genre genre = new Genre("Science Fiction");
        bookRepository.save(book);
        genreRepository.save(genre);

        BookGenre bookGenre = new BookGenre(book, genre);
        BookGenre savedBookGenre = bookGenreRepository.save(bookGenre);

        // Update the genre
        Genre newGenre = new Genre("Fantasy");
        genreRepository.save(newGenre);
        savedBookGenre.setGenre(newGenre);
        BookGenre updatedBookGenre = bookGenreRepository.save(savedBookGenre);

        BookGenre foundBookGenre = bookGenreRepository.findById(updatedBookGenre.getId()).orElse(null);
        assertNotNull(foundBookGenre);
        assertEquals(newGenre.getId(), foundBookGenre.getGenre().getId());
    }

    @Test
    void testDeleteBookGenre() {
        Book book = new Book("Test Book", "Test Description", null);
        Genre genre = new Genre("Science Fiction");
        bookRepository.save(book);
        genreRepository.save(genre);

        BookGenre bookGenre = new BookGenre(book, genre);
        BookGenre savedBookGenre = bookGenreRepository.save(bookGenre);

        bookGenreRepository.delete(savedBookGenre);
        BookGenre foundBookGenre = bookGenreRepository.findById(savedBookGenre.getId()).orElse(null);

        assertNull(foundBookGenre);
    }

    @Test
    void testCreateBookGenreByConstructor() {
        Book book = new Book("Test Book", "Test Description", null);
        Genre genre = new Genre("Science Fiction");
        bookRepository.save(book);
        genreRepository.save(genre);

        BookGenre bookGenre = new BookGenre(book, genre);
        bookGenreRepository.save(bookGenre);

        BookGenre foundBookGenre = bookGenreRepository.findById(bookGenre.getId()).orElse(null);
        assertNotNull(foundBookGenre);
        assertEquals(book.getId(), foundBookGenre.getBook().getId());
        assertEquals(genre.getId(), foundBookGenre.getGenre().getId());
    }
}
