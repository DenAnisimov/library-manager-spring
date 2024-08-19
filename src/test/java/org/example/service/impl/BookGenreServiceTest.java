package org.example.service.impl;

import org.example.dto.BookDTO;
import org.example.dto.BookGenreDTO;
import org.example.dto.GenreDTO;
import org.example.entity.Book;
import org.example.entity.BookGenre;
import org.example.entity.Genre;
import org.example.map.BookGenreMapper;
import org.example.repository.BookGenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookGenreServiceTest {

    @Mock
    private BookGenreMapper bookGenreMapper;

    @Mock
    private BookGenreRepository bookGenreRepository;

    @InjectMocks
    private BookGenreService bookGenreService;

    private BookGenreDTO bookGenreDTO;
    private BookGenre bookGenre;
    private Book book;
    private Genre genre;
    private BookDTO bookDTO;
    private GenreDTO genreDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");

        genre = new Genre();
        genre.setId(1L);
        genre.setName("Science Fiction");

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Sample Book");

        genreDTO = new GenreDTO();
        genreDTO.setId(1L);
        genreDTO.setName("Science Fiction");

        bookGenreDTO = new BookGenreDTO();
        bookGenreDTO.setId(1L);
        bookGenreDTO.setBookDTO(bookDTO);
        bookGenreDTO.setGenreDTO(genreDTO);

        bookGenre = new BookGenre();
        bookGenre.setId(1L);
        bookGenre.setBook(book);
        bookGenre.setGenre(genre);
    }

    @Test
    void testFindAll() {
        List<BookGenre> bookGenres = Arrays.asList(bookGenre);
        when(bookGenreRepository.findAll()).thenReturn(bookGenres);
        when(bookGenreMapper.mapToDTO(anyList())).thenReturn(Arrays.asList(bookGenreDTO));

        List<BookGenreDTO> result = bookGenreService.findAll();

        assertEquals(1, result.size());
        assertEquals(bookGenreDTO, result.get(0));
    }

    @Test
    void testFindById() {
        when(bookGenreRepository.findById(anyLong())).thenReturn(Optional.of(bookGenre));
        when(bookGenreMapper.mapToDTO(any(BookGenre.class))).thenReturn(bookGenreDTO);

        BookGenreDTO result = bookGenreService.findById(1L);

        assertEquals(bookGenreDTO, result);
    }

    @Test
    void testFindByIdEntityNotFound() {
        when(bookGenreRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            bookGenreService.findById(1L);
        });
    }

    @Test
    void testSave() {
        when(bookGenreMapper.mapToEntity(any(BookGenreDTO.class))).thenReturn(bookGenre);
        when(bookGenreRepository.save(any(BookGenre.class))).thenReturn(bookGenre);

        bookGenreService.save(bookGenreDTO);

        verify(bookGenreRepository, times(1)).save(bookGenre);
    }

    @Test
    void testDelete() {
        when(bookGenreMapper.mapToEntity(any(BookGenreDTO.class))).thenReturn(bookGenre);
        doNothing().when(bookGenreRepository).delete(any(BookGenre.class));

        bookGenreService.delete(bookGenreDTO);

        verify(bookGenreRepository, times(1)).delete(bookGenre);
    }
}