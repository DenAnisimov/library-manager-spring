package org.example.map;

import org.example.dto.BookDTO;
import org.example.dto.BookGenreDTO;
import org.example.dto.GenreDTO;
import org.example.entity.Book;
import org.example.entity.BookGenre;
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

class BookGenreMapperTest {

    @InjectMocks
    private BookGenreMapper bookGenreMapper;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private GenreMapper genreMapper;

    @BeforeEach
    void setUp() {
        bookGenreMapper = Mappers.getMapper(BookGenreMapper.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapToEntity() {
        BookGenreDTO bookGenreDTO = new BookGenreDTO();
        BookDTO bookDTO = new BookDTO("Book 1", "Description 1", null);
        GenreDTO genreDTO = new GenreDTO("Science Fiction");

        bookGenreDTO.setBookDTO(bookDTO);
        bookGenreDTO.setGenreDTO(genreDTO);

        when(bookMapper.mapToEntity(bookDTO)).thenReturn(new Book("Book 1", "Description 1", null));
        when(genreMapper.mapToEntity(genreDTO)).thenReturn(new Genre("Science Fiction"));

        BookGenre bookGenre = bookGenreMapper.mapToEntity(bookGenreDTO);

        assertNotNull(bookGenre);
        assertEquals(bookGenreDTO.getBookDTO().getTitle(), bookGenre.getBook().getTitle());
        assertEquals(bookGenreDTO.getGenreDTO().getName(), bookGenre.getGenre().getName());
    }

    @Test
    void testMapToDTO() {
        BookGenre bookGenre = new BookGenre();
        Book book = new Book("Book 1", "Description 1", null);
        Genre genre = new Genre("Science Fiction");

        bookGenre.setBook(book);
        bookGenre.setGenre(genre);

        when(bookMapper.mapToDTO(book)).thenReturn(new BookDTO("Book 1", "Description 1", null));
        when(genreMapper.mapToDTO(genre)).thenReturn(new GenreDTO("Science Fiction"));

        BookGenreDTO bookGenreDTO = bookGenreMapper.mapToDTO(bookGenre);

        assertNotNull(bookGenreDTO);
        assertEquals(bookGenre.getBook().getTitle(), bookGenreDTO.getBookDTO().getTitle());
        assertEquals(bookGenre.getGenre().getName(), bookGenreDTO.getGenreDTO().getName());
    }

    @Test
    void testMapToDTOList() {
        BookGenre bookGenre1 = new BookGenre();
        bookGenre1.setBook(new Book("Book 1", "Description 1", null));
        bookGenre1.setGenre(new Genre("Science Fiction"));

        BookGenre bookGenre2 = new BookGenre();
        bookGenre2.setBook(new Book("Book 2", "Description 2", null));
        bookGenre2.setGenre(new Genre("Fantasy"));

        List<BookGenre> bookGenres = Arrays.asList(bookGenre1, bookGenre2);

        when(bookMapper.mapToDTO(bookGenre1.getBook())).thenReturn(new BookDTO("Book 1", "Description 1", null));
        when(genreMapper.mapToDTO(bookGenre1.getGenre())).thenReturn(new GenreDTO("Science Fiction"));
        when(bookMapper.mapToDTO(bookGenre2.getBook())).thenReturn(new BookDTO("Book 2", "Description 2", null));
        when(genreMapper.mapToDTO(bookGenre2.getGenre())).thenReturn(new GenreDTO("Fantasy"));

        List<BookGenreDTO> bookGenreDTOs = bookGenreMapper.mapToDTO(bookGenres);

        assertNotNull(bookGenreDTOs);
        assertEquals(2, bookGenreDTOs.size());
        assertEquals(bookGenre1.getBook().getTitle(), bookGenreDTOs.get(0).getBookDTO().getTitle());
        assertEquals(bookGenre1.getGenre().getName(), bookGenreDTOs.get(0).getGenreDTO().getName());
        assertEquals(bookGenre2.getBook().getTitle(), bookGenreDTOs.get(1).getBookDTO().getTitle());
        assertEquals(bookGenre2.getGenre().getName(), bookGenreDTOs.get(1).getGenreDTO().getName());
    }
}