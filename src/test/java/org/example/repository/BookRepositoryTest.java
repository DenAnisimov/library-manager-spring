package org.example.repository;

import org.example.config.TestConfig;
import org.example.entity.Author;
import org.example.entity.Book;
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
class BookRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:latest").withDatabaseName("library_manager");

    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private AuthorRepository authorRepository;

    @BeforeAll
    static void setUp() {
        container.start();
    }

    @AfterAll
    static void tearDown() {
        container.close();
    }

    @BeforeEach
    void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        bookRepository = context.getBean(BookRepository.class);
        genreRepository = context.getBean(GenreRepository.class);
        authorRepository = context.getBean(AuthorRepository.class);
        BookGenreRepository bookGenreRepository = context.getBean(BookGenreRepository.class);
        bookGenreRepository.deleteAll();
        genreRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void testSaveAndFindBookWithoutGenres() {
        Author author = new Author();
        author.setName("John Doe");
        Author savedAuthor = authorRepository.save(author);

        Book book = new Book("Title", "Description", savedAuthor);
        Book savedBook = bookRepository.save(book);
        Book foundedBook = bookRepository.findById(savedBook.getId()).orElse(null);

        assertNotNull(foundedBook);
        assertEquals(savedBook.getId(), foundedBook.getId());
        assertEquals(savedBook.getAuthor().getName(), foundedBook.getAuthor().getName());
        assertEquals(savedBook.getTitle(), foundedBook.getTitle());
        assertEquals(savedBook.getDescription(), foundedBook.getDescription());
    }

    @Test
    void testSaveBookWithGenres() {
        Author author = new Author();
        author.setName("Jane Doe");
        Author savedAuthor = authorRepository.save(author);

        Genre genre1 = new Genre("Fiction");
        Genre genre2 = new Genre("Adventure");
        Genre savedGenre1 = genreRepository.save(genre1);
        Genre savedGenre2 = genreRepository.save(genre2);

        Book book = new Book("Book", "Description", savedAuthor);
        book.getGenres().add(savedGenre1);
        book.getGenres().add(savedGenre2);

        Book savedBook = bookRepository.save(book);

        Book foundBook = bookRepository.findById(savedBook.getId()).orElse(null);

        assertNotNull(foundBook);
        assertEquals(savedBook.getId(), foundBook.getId());
        assertEquals(savedBook.getTitle(), foundBook.getTitle());
        assertEquals(savedBook.getDescription(), foundBook.getDescription());
        assertEquals(savedBook.getAuthor().getName(), foundBook.getAuthor().getName());

        assertNotNull(foundBook.getGenres());
        assertEquals(2, foundBook.getGenres().size());
        assert(foundBook.getGenres().contains(savedGenre1));
        assert(foundBook.getGenres().contains(savedGenre2));
    }

    @Test
    void testUpdateBook() {
        Author author = new Author();
        author.setName("John Doe");
        Author savedAuthor = authorRepository.save(author);
        Book book = new Book("Title", "Description", savedAuthor);
        Book savedBook = bookRepository.save(book);
        savedBook.setTitle("Updated");
        bookRepository.save(savedBook);
        Book foundBook = bookRepository.findById(savedBook.getId()).orElse(null);
        assertNotNull(foundBook);
        assertEquals(savedBook.getId(), foundBook.getId());
        assertEquals(savedBook.getTitle(), foundBook.getTitle());
        assertEquals(savedBook.getDescription(), foundBook.getDescription());
        assertEquals(savedBook.getAuthor().getName(), foundBook.getAuthor().getName());
    }

    @Test
    void testDeleteBook() {
        Author author = new Author();
        author.setName("John Doe");
        Author savedAuthor = authorRepository.save(author);
        Book book = new Book("Title", "Description", savedAuthor);
        Book savedBook = bookRepository.save(book);
        authorRepository.deleteById(savedAuthor.getId());
        bookRepository.deleteById(savedBook.getId());
        Book foundBook = bookRepository.findById(savedBook.getId()).orElse(null);
        assertNull(foundBook);
    }
}
