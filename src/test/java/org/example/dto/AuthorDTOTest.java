package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthorDTOTest {

    @Test
    void testEquals() {
        AuthorDetailsDTO details1 = new AuthorDetailsDTO("1950-2000", "Biography 1");
        AuthorDTO author1 = new AuthorDTO("Author Name", details1);
        AuthorDTO author2 = new AuthorDTO("Author Name", details1);
        AuthorDTO author3 = new AuthorDTO("Another Author", details1);

        assertEquals(author1, author2);
        assertNotEquals(author1, author3);
        assertNotEquals(null, author1);
        assertNotEquals(author1, new Object());
    }

    @Test
    void testHashCode() {
        AuthorDetailsDTO details1 = new AuthorDetailsDTO("1950-2000", "Biography 1");
        AuthorDTO author1 = new AuthorDTO("Author Name", details1);
        AuthorDTO author2 = new AuthorDTO("Author Name", details1);
        AuthorDTO author3 = new AuthorDTO("Another Author", details1);

        assertEquals(author1.hashCode(), author2.hashCode());
        assertNotEquals(author1.hashCode(), author3.hashCode());
    }

    @Test
    void testToString() {
        AuthorDetailsDTO details = new AuthorDetailsDTO("1950-2000", "Biography 1");
        AuthorDTO author = new AuthorDTO("Author Name", details);

        String expectedString = "AuthorDTO{" +
                "id=null" +
                ", name='Author Name'" +
                ", authorDetailsDTO=" + details.toString() +
                ", books=[]" +
                '}';

        assertEquals(expectedString, author.toString());
    }

    @Test
    void testBooksInitialization() {
        AuthorDTO author = new AuthorDTO();
        assertTrue(author.getBooks().isEmpty(), "Books list should be initialized and empty.");
    }
}