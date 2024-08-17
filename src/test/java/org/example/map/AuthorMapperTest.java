package org.example.map;

import org.example.dto.AuthorDTO;
import org.example.dto.AuthorDetailsDTO;
import org.example.dto.BookDTO;
import org.example.entity.Author;
import org.example.entity.AuthorDetails;
import org.example.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class AuthorMapperTest {

    @InjectMocks
    private AuthorMapper authorMapper;

    @Mock
    private AuthorDetailsMapper authorDetailsMapper;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        authorMapper = Mappers.getMapper(AuthorMapper.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapToEntity() {
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO("1920-1990", "Brief Biography");
        AuthorDTO authorDTO = new AuthorDTO("Author Name", authorDetailsDTO);

        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setTitle("Book 1");
        BookDTO bookDTO2 = new BookDTO();
        bookDTO2.setTitle("Book 2");

        authorDTO.getBooks().add(bookDTO1);
        authorDTO.getBooks().add(bookDTO2);

        when(authorDetailsMapper.mapToEntity(authorDetailsDTO)).thenReturn(new AuthorDetails("1920-1990", "Brief Biography"));
        when(bookMapper.mapToEntity(bookDTO1)).thenReturn(new Book("Book 1", "Description 1", null));
        when(bookMapper.mapToEntity(bookDTO2)).thenReturn(new Book("Book 2", "Description 2", null));

        Author author = authorMapper.mapToEntity(authorDTO);

        assertNotNull(author);
        assertEquals(authorDTO.getName(), author.getName());
        assertEquals(authorDTO.getAuthorDetailsDTO().getLifeYears(), author.getAuthorDetails().getLifeYears());
        assertEquals(2, author.getBooks().size());
        assertEquals(authorDTO.getBooks().get(0).getTitle(), author.getBooks().get(0).getTitle());
    }

    @Test
    void testMapToDTO() {
        AuthorDetails authorDetails = new AuthorDetails("1920-1990", "Brief Biography");
        Author author = new Author("Author Name", authorDetails);

        Book book1 = new Book("Book 1", "Description 1", author);
        Book book2 = new Book("Book 2", "Description 2", author);

        author.getBooks().add(book1);
        author.getBooks().add(book2);

        when(authorDetailsMapper.mapToDTO(authorDetails)).thenReturn(new AuthorDetailsDTO("1920-1990", "Brief Biography"));
        when(bookMapper.mapToDTO(book1)).thenReturn(new BookDTO("Book 1", "Description 1", null));
        when(bookMapper.mapToDTO(book2)).thenReturn(new BookDTO("Book 2", "Description 2", null));

        AuthorDTO authorDTO = authorMapper.mapToDTO(author);

        assertNotNull(authorDTO);
        assertEquals(author.getName(), authorDTO.getName());
        assertEquals(author.getAuthorDetails().getLifeYears(), authorDTO.getAuthorDetailsDTO().getLifeYears());
        assertEquals(2, authorDTO.getBooks().size());
        assertEquals(author.getBooks().get(0).getTitle(), authorDTO.getBooks().get(0).getTitle());
    }

    @Test
    void testMapToDTOList() {
        AuthorDetails authorDetails = new AuthorDetails("1920-1990", "Brief Biography");
        Author author = new Author("Author Name", authorDetails);
        List<Author> authors = new ArrayList<>();
        authors.add(author);

        when(authorDetailsMapper.mapToDTO(authorDetails)).thenReturn(new AuthorDetailsDTO("1920-1990", "Brief Biography"));

        List<AuthorDTO> authorDTOs = authorMapper.mapToDTO(authors);

        assertNotNull(authorDTOs);
        assertEquals(1, authorDTOs.size());
        assertEquals(author.getName(), authorDTOs.get(0).getName());
    }
}
