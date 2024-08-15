package org.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BookTest {

    @Test
    void testEquals() {
        Author author1 = new Author("Author Name", new AuthorDetails("1950-2000", "Biography 1"));
        Author author2 = new Author("Author Name", new AuthorDetails("1950-2000", "Biography 1"));

        Book book1 = new Book("Book Title", "Book Description", author1);

        Book book2 = new Book("Book Title", "Book Description", author2);

        Book book3 = new Book("Different Title", "Different Description", author1);

        assertEquals(book1, book2);
        assertNotEquals(book1, book3);
        assertNotEquals(null, book1);
        assertNotEquals(book1, new Object());
    }

    @Test
    void testHashCode() {
        Author author1 = new Author("Author Name", new AuthorDetails("1950-2000", "Biography 1"));
        Author author2 = new Author("Author Name", new AuthorDetails("1950-2000", "Biography 1"));

        Book book1 = new Book("Book Title", "Book Description", author1);

        Book book2 = new Book("Book Title", "Book Description", author2);

        Book book3 = new Book("Different Title", "Different Description", author1);

        assertEquals(book1.hashCode(), book2.hashCode());
        assertNotEquals(book1.hashCode(), book3.hashCode());
    }

    @Test
    void testToString() {
        Author author = new Author("Author Name", new AuthorDetails("1950-2000", "Biography 1"));
        Book book = new Book("Book Title", "Book Description", author);

        String expectedString = "Book{" +
                "id=null" +
                ", title='Book Title'" +
                ", description='Book Description'" +
                ", authorId=" + author.getId() +
                ", genres=[]" +
                '}';

        assertEquals(expectedString, book.toString());
    }
}
