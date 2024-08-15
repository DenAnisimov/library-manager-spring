package org.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BookGenreTest {

    @Test
    void testEquals() {
        Book book1 = new Book("Book1", "Description1", null);
        Book book2 = new Book("Book2", "Description2", null);
        Genre genre1 = new Genre("Fantasy");
        Genre genre2 = new Genre("Science Fiction");

        BookGenre bookGenre1 = new BookGenre(book1, genre1);
        BookGenre bookGenre2 = new BookGenre(book1, genre1);
        BookGenre bookGenre3 = new BookGenre(book2, genre2);

        assertEquals(bookGenre1, bookGenre2);

        assertNotEquals(bookGenre1, bookGenre3);
        assertNotEquals(null, bookGenre1);
        assertNotEquals(bookGenre1, new Object());
    }

    @Test
    void testHashCode() {
        Book book1 = new Book("Book1", "Description1", null);
        Book book2 = new Book("Book2", "Description2", null);
        Genre genre1 = new Genre("Fantasy");
        Genre genre2 = new Genre("Science Fiction");

        BookGenre bookGenre1 = new BookGenre(book1, genre1);
        BookGenre bookGenre2 = new BookGenre(book1, genre1);
        BookGenre bookGenre3 = new BookGenre(book2, genre2);

        assertEquals(bookGenre1.hashCode(), bookGenre2.hashCode());

        assertNotEquals(bookGenre1.hashCode(), bookGenre3.hashCode());
    }

    @Test
    void testToString() {
        Book book = new Book("Book1", "Description1", null);
        Genre genre = new Genre("Fantasy");

        BookGenre bookGenre = new BookGenre(book, genre);

        String expectedString = "BookGenre{" +
                "id=null" +
                ", book=" + book +
                ", genre=" + genre +
                '}';

        assertEquals(expectedString, bookGenre.toString());
    }
}