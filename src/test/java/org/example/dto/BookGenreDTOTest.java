package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BookGenreDTOTest {

    @Test
    void testEquals() {
        BookDTO book1 = new BookDTO("Book Title", "Description", new AuthorDTO("Author Name", new AuthorDetailsDTO("1950-2000", "Biography 1")));
        GenreDTO genre1 = new GenreDTO("Fiction");

        BookGenreDTO bookGenre1 = new BookGenreDTO(book1, genre1);
        BookGenreDTO bookGenre2 = new BookGenreDTO(book1, genre1);
        BookGenreDTO bookGenre3 = new BookGenreDTO(new BookDTO("Another Book", "Another Description", new AuthorDTO()), new GenreDTO("Non-Fiction"));

        assertEquals(bookGenre1, bookGenre2);
        assertNotEquals(bookGenre1, bookGenre3);
        assertNotEquals(null, bookGenre1);
        assertNotEquals(bookGenre1, new Object());
    }

    @Test
    void testHashCode() {
        BookDTO book1 = new BookDTO("Book Title", "Description", new AuthorDTO("Author Name", new AuthorDetailsDTO("1950-2000", "Biography 1")));
        GenreDTO genre1 = new GenreDTO("Fiction");

        BookGenreDTO bookGenre1 = new BookGenreDTO(book1, genre1);
        BookGenreDTO bookGenre2 = new BookGenreDTO(book1, genre1);
        BookGenreDTO bookGenre3 = new BookGenreDTO(new BookDTO("Another Book", "Another Description", new AuthorDTO()), new GenreDTO("Non-Fiction"));

        assertEquals(bookGenre1.hashCode(), bookGenre2.hashCode());
        assertNotEquals(bookGenre1.hashCode(), bookGenre3.hashCode());
    }

    @Test
    void testToString() {
        BookDTO book = new BookDTO("Book Title", "Description", new AuthorDTO("Author Name", new AuthorDetailsDTO("1950-2000", "Biography 1")));
        GenreDTO genre = new GenreDTO("Fiction");

        BookGenreDTO bookGenre = new BookGenreDTO(book, genre);

        String expectedString = "BookGenreDTO{" +
                "id=null" +
                ", book=" + book +
                ", genre=" + genre +
                '}';

        assertEquals(expectedString, bookGenre.toString());
    }
}