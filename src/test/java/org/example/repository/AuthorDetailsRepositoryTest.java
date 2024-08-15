package org.example.repository;

import org.example.config.TestConfig;
import org.example.entity.AuthorDetails;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Tag("DockerRequired")
class AuthorDetailsRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:latest").withDatabaseName("library_manager");

    private AuthorDetailsRepository authorDetailsRepository;
    private AuthorRepository authorRepository;

    @BeforeAll
    public static void setUp() {
        container.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    @BeforeEach
    void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        authorDetailsRepository = context.getBean(AuthorDetailsRepository.class);
        authorRepository = context.getBean(AuthorRepository.class);
        authorRepository.deleteAll();
        authorDetailsRepository.deleteAll();
    }

    @Test
    void testSaveAndFindByIdAuthorDetails() {
        AuthorDetails authorDetails = new AuthorDetails();
        authorDetails.setLifeYears("1900-1950");
        authorDetails.setBriefBiography("Brief Biography");
        AuthorDetails savedAuthorDetails = authorDetailsRepository.save(authorDetails);
        AuthorDetails foundAuthorDetails = authorDetailsRepository.findById(savedAuthorDetails.getId()).get();
        assertNotNull(foundAuthorDetails);
        assertEquals(authorDetails.getLifeYears(), foundAuthorDetails.getLifeYears());
        assertEquals(authorDetails.getBriefBiography(), foundAuthorDetails.getBriefBiography());
    }

    @Test
    void testUpdateAuthorDetails() {
        AuthorDetails authorDetails = new AuthorDetails();
        authorDetails.setLifeYears("1900-1950");
        authorDetails.setBriefBiography("Brief Biography");
        AuthorDetails savedAuthorDetails = authorDetailsRepository.save(authorDetails);
        savedAuthorDetails.setLifeYears("1910-1960");
        AuthorDetails updatedAuthorDetails = authorDetailsRepository.save(savedAuthorDetails);
        AuthorDetails foundAuthorDetails = authorDetailsRepository.findById(updatedAuthorDetails.getId()).get();
        assertEquals(authorDetails.getLifeYears(), foundAuthorDetails.getLifeYears());
        assertNotNull(foundAuthorDetails);
    }

    @Test
    void testDeleteAuthorDetails() {
        AuthorDetails authorDetails = new AuthorDetails();
        authorDetails.setLifeYears("1900-1950");
        authorDetails.setBriefBiography("Brief Biography");
        AuthorDetails savedAuthorDetails = authorDetailsRepository.save(authorDetails);
        authorDetailsRepository.delete(savedAuthorDetails);
        AuthorDetails foundAuthorDetails = authorDetailsRepository.findById(savedAuthorDetails.getId()).orElse(null);
        assertNull(foundAuthorDetails);
    }

    @Test
    void testCreateAuthorDetailsWithConstruct() {
        AuthorDetails authorDetails = new AuthorDetails("1950-1960", "Brief Biography");
        AuthorDetails savedAuthorDetails = authorDetailsRepository.save(authorDetails);
        AuthorDetails foundAuthorDetails = authorDetailsRepository.findById(savedAuthorDetails.getId()).get();
        assertNotNull(foundAuthorDetails);
        assertEquals(authorDetails.getLifeYears(), foundAuthorDetails.getLifeYears());
        assertNotNull(foundAuthorDetails.getBriefBiography());
    }
}
