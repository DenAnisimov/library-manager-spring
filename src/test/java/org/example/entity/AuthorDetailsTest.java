package org.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDetailsTest {

    @Test
    void testAuthorDetailsNoArgsConstructor() {
        AuthorDetails authorDetails = new AuthorDetails();
        assertNotNull(authorDetails);
    }

    @Test
    void testAuthorDetailsAllArgsConstructor() {
        AuthorDetails authorDetails = new AuthorDetails(1L, "1965-", "Biography");

        assertNotNull(authorDetails);
        assertEquals(1L, authorDetails.getId());
        assertEquals("1965-", authorDetails.getLifeYears());
        assertEquals("Biography", authorDetails.getBriefBiography());
    }

    @Test
    void testAuthorDetailsBuilder() {
        AuthorDetails authorDetails = AuthorDetails.builder()
                .id(1L)
                .lifeYears("1965-")
                .briefBiography("Biography")
                .build();

        assertNotNull(authorDetails);
        assertEquals(1L, authorDetails.getId());
        assertEquals("1965-", authorDetails.getLifeYears());
        assertEquals("Biography", authorDetails.getBriefBiography());
    }
}