package org.example.map;

import org.example.dto.BookDTO;
import org.example.dto.GenreDTO;
import org.example.entity.Book;
import org.example.entity.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class GenreMapperTest {

    @InjectMocks
    private GenreMapper genreMapper;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        genreMapper = Mappers.getMapper(GenreMapper.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapToEntity() {
        GenreDTO genreDTO = new GenreDTO("Science Fiction");

        BookDTO bookDTO1 = new BookDTO("Book 1", "Description 1", null);
        BookDTO bookDTO2 = new BookDTO("Book 2", "Description 2", null);

        genreDTO.getBookDTOs().add(bookDTO1);
        genreDTO.getBookDTOs().add(bookDTO2);

        when(bookMapper.mapToEntity(bookDTO1)).thenReturn(new Book("Book 1", "Description 1", null));
        when(bookMapper.mapToEntity(bookDTO2)).thenReturn(new Book("Book 2", "Description 2", null));

        Genre genre = genreMapper.mapToEntity(genreDTO);

        assertNotNull(genre);
        assertEquals(genreDTO.getName(), genre.getName());
        assertEquals(2, genre.getBooks().size());
        assertEquals(genreDTO.getBookDTOs().iterator().next().getTitle(), genre.getBooks().iterator().next().getTitle());
    }

    @Test
    void testMapToDTO() {
        Genre genre = new Genre("Science Fiction");

        Book book1 = new Book("Book 1", "Description 1", null);
        Book book2 = new Book("Book 2", "Description 2", null);

        genre.getBooks().add(book1);
        genre.getBooks().add(book2);

        when(bookMapper.mapToDTO(book1)).thenReturn(new BookDTO("Book 1", "Description 1", null));
        when(bookMapper.mapToDTO(book2)).thenReturn(new BookDTO("Book 2", "Description 2", null));

        GenreDTO genreDTO = genreMapper.mapToDTO(genre);

        assertNotNull(genreDTO);
        assertEquals(genre.getName(), genreDTO.getName());
        assertEquals(2, genreDTO.getBookDTOs().size());
        assertEquals(genre.getBooks().iterator().next().getTitle(), genreDTO.getBookDTOs().iterator().next().getTitle());
    }

    @Test
    void testMapToDTOList() {
        Genre genre1 = new Genre("Science Fiction");
        Genre genre2 = new Genre("Fantasy");

        List<Genre> genres = Arrays.asList(genre1, genre2);

        List<GenreDTO> genreDTOs = genreMapper.mapToDTO(genres);

        assertNotNull(genreDTOs);
        assertEquals(2, genreDTOs.size());
        assertEquals(genre1.getName(), genreDTOs.get(0).getName());
        assertEquals(genre2.getName(), genreDTOs.get(1).getName());
    }
}