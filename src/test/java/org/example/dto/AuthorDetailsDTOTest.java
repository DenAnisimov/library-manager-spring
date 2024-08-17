package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AuthorDetailsDTOTest {

    @Test
    void testEquals() {
        AuthorDetailsDTO dto1 = new AuthorDetailsDTO("1950-2000", "Biography 1");
        AuthorDetailsDTO dto2 = new AuthorDetailsDTO("1950-2000", "Biography 1");
        AuthorDetailsDTO dto3 = new AuthorDetailsDTO("1900-2000", "Biography 2");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(null, dto1);
        assertNotEquals(dto1, new Object());
    }

    @Test
    void testHashCode() {
        AuthorDetailsDTO dto1 = new AuthorDetailsDTO("1950-2000", "Biography 1");
        AuthorDetailsDTO dto2 = new AuthorDetailsDTO("1950-2000", "Biography 1");
        AuthorDetailsDTO dto3 = new AuthorDetailsDTO("1900-2000", "Biography 2");

        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testToString() {
        AuthorDetailsDTO dto = new AuthorDetailsDTO("1950-2000", "Biography 1");

        String expectedString = "AuthorDetailsDTO{" +
                "id=null" +
                ", lifeYears='1950-2000'" +
                ", briefBiography='Biography 1'" +
                '}';

        assertEquals(expectedString, dto.toString());
    }
}
