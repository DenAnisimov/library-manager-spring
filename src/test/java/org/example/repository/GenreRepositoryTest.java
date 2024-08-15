package org.example.repository;

import org.example.config.TestConfig;
import org.example.entity.Genre;
import org.example.entity.Book;
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
class GenreRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:latest").withDatabaseName("library_manager");

    private GenreRepository genreRepository;
    private BookRepository bookRepository;
    private BookGenreRepository genreGenreRepository;

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
        genreRepository = context.getBean(GenreRepository.class);
        bookRepository = context.getBean(BookRepository.class);
        genreGenreRepository = context.getBean(BookGenreRepository.class);
        genreGenreRepository.deleteAll();
        genreRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    void testSaveAndFindGenre() {
        Genre genre = new Genre("Science Fiction");
        Genre savedGenre = genreRepository.save(genre);
        Genre foundGenre = genreRepository.findById(savedGenre.getId()).get();
        assertEquals(genre.getName(), foundGenre.getName());
    }

    @Test
    void testUpdateGenre() {
        Genre genre = new Genre("Science Fiction");
        Genre savedGenre = genreRepository.save(genre);
        savedGenre.setName("Fantasy");
        Genre updatedGenre = genreRepository.save(savedGenre);
        Genre foundGenre = genreRepository.findById(updatedGenre.getId()).orElse(null);
        assertNotNull(foundGenre);
        assertEquals("Fantasy", foundGenre.getName());
    }

    @Test
    void testDeleteGenre() {
        Genre genre = new Genre("Science Fiction");
        Genre savedGenre = genreRepository.save(genre);
        genreRepository.delete(savedGenre);
        Genre foundGenre = genreRepository.findById(savedGenre.getId()).orElse(null);
        assertNull(foundGenre);
    }

    @Test
    void testCreateGenreByConstructor() {
        Genre genre = new Genre("Science Fiction");
        genreRepository.save(genre);
        Genre foundGenre = genreRepository.findById(genre.getId()).orElse(null);
        assertNotNull(foundGenre);
        assertEquals("Science Fiction", foundGenre.getName());
    }

    @Test
    void testGetGenreWithBooks() {
        Genre genre = new Genre("Science Fiction");
        Genre savedGenre = genreRepository.save(genre);

        Book book1 = new Book("Book 1", "Description 1", null);
        book1.getGenres().add(savedGenre);
        Book book2 = new Book("Book 2", "Description 2", null);
        book2.getGenres().add(savedGenre);

        bookRepository.save(book1);
        bookRepository.save(book2);

        Genre foundGenre = genreRepository.findById(savedGenre.getId()).orElse(null);
        assert foundGenre != null;
        assertEquals(2, foundGenre.getBooks().size());
    }
}