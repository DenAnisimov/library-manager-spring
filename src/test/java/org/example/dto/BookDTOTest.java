package org.example.dto;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookDTOTest {

    @Test
    void testEquals() {
        AuthorDTO author1 = new AuthorDTO("Author Name", new AuthorDetailsDTO("1950-2000", "Biography 1"));
        BookDTO book1 = new BookDTO("Book Title", "Book Description", author1);
        BookDTO book2 = new BookDTO("Book Title", "Book Description", author1);
        BookDTO book3 = new BookDTO("Another Book Title", "Another Description", author1);

        assertEquals(book1, book2);
        assertNotEquals(book1, book3);
        assertNotEquals(null, book1);
        assertNotEquals(book1, new Object());
    }

    @Test
    void testHashCode() {
        AuthorDTO author1 = new AuthorDTO("Author Name", new AuthorDetailsDTO("1950-2000", "Biography 1"));
        BookDTO book1 = new BookDTO("Book Title", "Book Description", author1);
        BookDTO book2 = new BookDTO("Book Title", "Book Description", author1);
        BookDTO book3 = new BookDTO("Another Book Title", "Another Description", author1);

        assertEquals(book1.hashCode(), book2.hashCode());
        assertNotEquals(book1.hashCode(), book3.hashCode());
    }

    @Test
    void testToString() {
        AuthorDTO author = new AuthorDTO("Author Name", new AuthorDetailsDTO("1950-2000", "Biography 1"));
        BookDTO book = new BookDTO("Book Title", "Book Description", author);

        String expectedString = "BookDTO{" +
                "id=null" +
                ", title='Book Title'" +
                ", description='Book Description'" +
                ", authorDTOId=" + (author.getId() != null ? author.getId() : "null") +
                ", genreDTOs=" + new HashSet<>() +
                '}';

        assertEquals(expectedString, book.toString());
    }

    @Test
    void testGenresInitialization() {
        BookDTO book = new BookDTO();
        assertTrue(book.getGenreDTOs().isEmpty(), "Genres set should be initialized and empty.");
    }

    @Test
    void testAddGenre() {
        BookDTO book = new BookDTO();
        GenreDTO genre = new GenreDTO("Fiction");

        book.getGenreDTOs().add(genre);
        Set<GenreDTO> expectedGenres = new HashSet<>();
        expectedGenres.add(genre);

        assertEquals(expectedGenres, book.getGenreDTOs());
    }
}