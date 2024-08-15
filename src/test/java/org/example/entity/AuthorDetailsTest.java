package org.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AuthorDetailsTest {

    @Test
    void testEquals() {
        AuthorDetails details1 = new AuthorDetails("1950-2000", "Biography 1");
        AuthorDetails details2 = new AuthorDetails("1950-2000", "Biography 1");
        AuthorDetails details3 = new AuthorDetails("1900-2000", "Biography 2");

        assertEquals(details1, details2);
        assertNotEquals(details1, details3);
        assertNotEquals(null, details1);
        assertNotEquals(details1, new Object());
    }

    @Test
    void testHashCode() {
        AuthorDetails details1 = new AuthorDetails("1950-2000", "Biography 1");
        AuthorDetails details2 = new AuthorDetails("1950-2000", "Biography 1");
        AuthorDetails details3 = new AuthorDetails("1900-2000", "Biography 2");

        assertEquals(details1.hashCode(), details2.hashCode());
        assertNotEquals(details1.hashCode(), details3.hashCode());
    }

    @Test
    void testToString() {
        AuthorDetails details = new AuthorDetails("1950-2000", "Biography 1");

        String expectedString = "AuthorDetails{" +
                "id=null" +
                ", lifeYears='1950-2000'" +
                ", briefBiography='Biography 1'" +
                '}';

        assertEquals(expectedString, details.toString());
    }
}
