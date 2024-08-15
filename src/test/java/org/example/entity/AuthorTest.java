package org.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AuthorTest {

    @Test
    void testEquals() {
        Author author1 = new Author("Author Name", new AuthorDetails());
        Author author2 = new Author("Author Name", new AuthorDetails());
        Author author3 = new Author("Different Author", new AuthorDetails());

        assertEquals(author1, author2);
        assertNotEquals(author1, author3);
        assertNotEquals(null, author1);
        assertNotEquals(author1, new Object());
    }

    @Test
    void testHashCode() {
        Author author1 = new Author("Author Name", new AuthorDetails());
        Author author2 = new Author("Author Name", new AuthorDetails());
        Author author3 = new Author("Different Author", new AuthorDetails());

        assertEquals(author1.hashCode(), author2.hashCode());
        assertNotEquals(author1.hashCode(), author3.hashCode());
    }

    @Test
    void testToString() {
        AuthorDetails authorDetails = new AuthorDetails();
        Author author = new Author("Author Name", authorDetails);

        String expectedString = "Author{" +
                "id=null" +
                ", name='Author Name'" +
                ", authorDetails=" + authorDetails +
                ", books=[]" +
                '}';

        assertEquals(expectedString, author.toString());
    }
}
