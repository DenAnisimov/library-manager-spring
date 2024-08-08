package org.example.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    @Test
    void testAuthorNoArgsConstructor() {
        Author author = new Author();
        assertNotNull(author);
    }

    @Test
    void testAuthorAllArgsConstructor() {
        AuthorDetails authorDetails = new AuthorDetails(1L, "1965-", "Biography");
        Author author = new Author(1L, "J.K. Rowling", authorDetails, new ArrayList<>());

        assertNotNull(author);
        assertEquals(1L, author.getId());
        assertEquals("J.K. Rowling", author.getName());
        assertEquals(authorDetails, author.getAuthorDetails());
        assertNotNull(author.getBooks());
    }

    @Test
    void testAuthorBuilder() {
        Author author = Author.builder()
                .id(1L)
                .name("J.K. Rowling")
                .authorDetails(AuthorDetails.builder()
                        .id(1L)
                        .lifeYears("1965-")
                        .briefBiography("Biography")
                        .build())
                .books(new ArrayList<>())
                .build();

        assertNotNull(author);
        assertEquals(1L, author.getId());
        assertEquals("J.K. Rowling", author.getName());
        assertNotNull(author.getAuthorDetails());
        assertEquals(1L, author.getAuthorDetails().getId());
        assertEquals("1965-", author.getAuthorDetails().getLifeYears());
        assertEquals("Biography", author.getAuthorDetails().getBriefBiography());
        assertNotNull(author.getBooks());
    }
}
