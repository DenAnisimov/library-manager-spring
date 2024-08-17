package org.example.map;

import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;
import org.example.dto.GenreDTO;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class BookMapperTest {

    @InjectMocks
    private BookMapperImpl bookMapper;  // Реализация будет подставлена

    @Mock
    private AuthorMapper authorMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapToDTO() {
        Author author = new Author("Author Name", null);
        Book book = new Book("Book Title", "Book Description", author);

        Genre genre1 = new Genre("Genre 1");
        Genre genre2 = new Genre("Genre 2");
        book.getGenres().add(genre1);
        book.getGenres().add(genre2);

        AuthorDTO authorDTO = new AuthorDTO("Author Name", null);
        when(authorMapper.mapToDTO(author)).thenReturn(authorDTO);

        BookDTO bookDTO = bookMapper.mapToDTO(book);

        assertNotNull(bookDTO);
        assertEquals(book.getTitle(), bookDTO.getTitle());
        assertEquals(book.getDescription(), bookDTO.getDescription());
        assertEquals(authorDTO, bookDTO.getAuthorDTO());
        assertEquals(2, bookDTO.getGenreDTOs().size());
    }

    @Test
    void testMapToEntity() {
        AuthorDTO authorDTO = new AuthorDTO("Author Name", null);
        BookDTO bookDTO = new BookDTO("Book Title", "Book Description", authorDTO);

        GenreDTO genreDTO1 = new GenreDTO("Genre 1");
        GenreDTO genreDTO2 = new GenreDTO("Genre 2");
        bookDTO.getGenreDTOs().add(genreDTO1);
        bookDTO.getGenreDTOs().add(genreDTO2);

        Author author = new Author("Author Name", null);
        when(authorMapper.mapToEntity(authorDTO)).thenReturn(author);

        Book book = bookMapper.mapToEntity(bookDTO);

        assertNotNull(book);
        assertEquals(bookDTO.getTitle(), book.getTitle());
        assertEquals(bookDTO.getDescription(), book.getDescription());
        assertEquals(author, book.getAuthor());
        assertEquals(2, book.getGenres().size());
    }
}