package org.example.dto;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenreDTOTest {

    @Test
    void testEquals() {
        GenreDTO genre1 = new GenreDTO("Fiction");
        GenreDTO genre2 = new GenreDTO("Fiction");
        GenreDTO genre3 = new GenreDTO("Non-Fiction");

        assertEquals(genre1, genre2);
        assertNotEquals(genre1, genre3);
        assertNotEquals(null, genre1);
        assertNotEquals(genre1, new Object());
    }

    @Test
    void testHashCode() {
        GenreDTO genre1 = new GenreDTO("Fiction");
        GenreDTO genre2 = new GenreDTO("Fiction");
        GenreDTO genre3 = new GenreDTO("Non-Fiction");

        assertEquals(genre1.hashCode(), genre2.hashCode());
        assertNotEquals(genre1.hashCode(), genre3.hashCode());
    }

    @Test
    void testToString() {
        GenreDTO genre = new GenreDTO("Fiction");

        String expectedString = "GenreDTO{" +
                "id=null" +
                ", name='Fiction'" +
                ", booksDTOs=" + new HashSet<>() +
                '}';

        assertEquals(expectedString, genre.toString());
    }

    @Test
    void testBooksDTOsInitialization() {
        GenreDTO genre = new GenreDTO();
        assertTrue(genre.getBookDTOs().isEmpty(), "BooksDTOs set should be initialized and empty.");
    }

    @Test
    void testAddBookDTO() {
        GenreDTO genre = new GenreDTO("Fiction");
        BookDTO book = new BookDTO("Sample Book", "Sample Description", new AuthorDTO("Author Name", new AuthorDetailsDTO("1950-2000", "Biography 1")));

        genre.getBookDTOs().add(book);
        Set<BookDTO> expectedBooks = new HashSet<>();
        expectedBooks.add(book);

        assertEquals(expectedBooks, genre.getBookDTOs());
    }
}
