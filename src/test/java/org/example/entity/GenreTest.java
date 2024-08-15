package org.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

 class GenreTest {

    @Test
     void testEquals() {
        Genre genre1 = new Genre("Fantasy");

        Genre genre2 = new Genre("Fantasy");

        Genre genre3 = new Genre("Science Fiction");

        assertEquals(genre1, genre2);
        assertNotEquals(genre1, genre3);
        assertNotEquals(null, genre1);
        assertNotEquals(genre1, new Object());
    }

    @Test
     void testHashCode() {
        Genre genre1 = new Genre("Fantasy");

        Genre genre2 = new Genre("Fantasy");

        Genre genre3 = new Genre("Science Fiction");

        assertEquals(genre1.hashCode(), genre2.hashCode());
        assertNotEquals(genre1.hashCode(), genre3.hashCode());
    }

    @Test
     void testToString() {
        Genre genre = new Genre("Fantasy");

        String expectedString = "Genre{" +
                "id=null" +
                ", name='Fantasy'" +
                ", books=[]" +
                '}';

        assertEquals(expectedString, genre.toString());
    }
}
