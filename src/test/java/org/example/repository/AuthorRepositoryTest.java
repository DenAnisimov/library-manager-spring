package org.example.repository;

import org.example.config.TestConfig;
import org.example.entity.Author;
import org.example.entity.AuthorDetails;
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
class AuthorRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:latest").withDatabaseName("library_manager");

    private AuthorRepository authorRepository;
    private AuthorDetailsRepository authorDetailsRepository;
    private BookRepository bookRepository;

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
        authorRepository = context.getBean(AuthorRepository.class);
        authorDetailsRepository = context.getBean(AuthorDetailsRepository.class);
        bookRepository = context.getBean(BookRepository.class);
        authorRepository.deleteAll();
        authorDetailsRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    void testSaveAndFindAuthor() {
        AuthorDetails authorDetails = new AuthorDetails("Test", "Test");

        Author author = new Author();
        author.setName("Jhon Doe");
        author.setAuthorDetails(authorDetails);

        Author savedAuthor = authorRepository.save(author);
        Author foundAuthor = authorRepository.findById(savedAuthor.getId()).get();
        assertEquals(author.getName(), foundAuthor.getName());
    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setName("John Doe");
        Author savedAuthor = authorRepository.save(author);
        savedAuthor.setName("Jane Doe");
        Author updatedAuthor = authorRepository.save(savedAuthor);
        Author foundAuthor = authorRepository.findById(updatedAuthor.getId()).orElse(null);
        assertNotNull(foundAuthor);
        assertEquals("Jane Doe", foundAuthor.getName());
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setName("John Doe");
        Author savedAuthor = authorRepository.save(author);
        authorRepository.delete(author);
        Author foundAuthor = authorRepository.findById(savedAuthor.getId()).orElse(null);
        assertNull(foundAuthor);
    }

    @Test
    void testCreateAuthorByConstructor() {
        AuthorDetails authorDetails = new AuthorDetails("test", "test");
        Author author = new Author("Jhon Doe", authorDetails);
        authorRepository.save(author);
        Author foundAuthor = authorRepository.findById(author.getId()).orElse(null);
        assertNotNull(foundAuthor);
        assertEquals("Jhon Doe", foundAuthor.getName());
        assertEquals(authorDetails, foundAuthor.getAuthorDetails());
    }

    @Test
    void testGetAuthorWithBooks() {
        Author author = new Author("Jhon Doe", new AuthorDetails("test", "test"));
        Author savedAuthor = authorRepository.save(author);

        Book book1 = new Book();
        book1.setTitle("Test1");
        book1.setDescription("Test1");
        book1.setAuthor(author);
        Book book2 = new Book();
        book2.setTitle("Test2");
        book2.setDescription("Test2");
        book2.setAuthor(author);
        bookRepository.save(book1);
        bookRepository.save(book2);
        Author foundAuthor = authorRepository.findById(savedAuthor.getId()).orElse(null);
        assert foundAuthor != null;
        assertEquals(2, foundAuthor.getBooks().size());
    }
}
