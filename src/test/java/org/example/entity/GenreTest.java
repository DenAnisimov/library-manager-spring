package org.example.entity;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {

    @Test
    void testGenreNoArgsConstructor() {
        Genre genre = new Genre();
        assertNotNull(genre);
    }

    @Test
    void testGenreAllArgsConstructor() {
        Genre genre = new Genre(1L, "Fantasy", new HashSet<>());

        assertNotNull(genre);
        assertEquals(1L, genre.getId());
        assertEquals("Fantasy", genre.getName());
        assertNotNull(genre.getBooks());
    }

    @Test
    void testGenreBuilder() {
        Genre genre = Genre.builder()
                .id(1L)
                .name("Fantasy")
                .books(new HashSet<>())
                .build();

        assertNotNull(genre);
        assertEquals(1L, genre.getId());
        assertEquals("Fantasy", genre.getName());
        assertNotNull(genre.getBooks());
    }
}