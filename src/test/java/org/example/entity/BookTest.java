package org.example.entity;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testBookNoArgsConstructor() {
        Book book = new Book();
        assertNotNull(book);
    }

    @Test
    void testBookAllArgsConstructor() {
        Author author = new Author(1L, "J.K. Rowling", null, null);
        Book book = new Book(1L, "Harry Potter", "A wizard boy", author, new HashSet<>());

        assertNotNull(book);
        assertEquals(1L, book.getId());
        assertEquals("Harry Potter", book.getTitle());
        assertEquals("A wizard boy", book.getDescription());
        assertEquals(author, book.getAuthor());
        assertNotNull(book.getGenres());
    }

    @Test
    void testBookBuilder() {
        Author author = Author.builder().id(1L).name("J.K. Rowling").build();
        Book book = Book.builder()
                .id(1L)
                .title("Harry Potter")
                .description("A wizard boy")
                .author(author)
                .genres(new HashSet<>())
                .build();

        assertNotNull(book);
        assertEquals(1L, book.getId());
        assertEquals("Harry Potter", book.getTitle());
        assertEquals("A wizard boy", book.getDescription());
        assertEquals(author, book.getAuthor());
        assertNotNull(book.getGenres());
    }
}