package org.example.service.impl;

import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.map.AuthorMapper;
import org.example.map.BookMapper;
import org.example.repository.BookRepository;
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
class BookServiceTest {

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private BookService bookService;

    private BookDTO bookDTO;
    private Book book;
    private AuthorDTO authorDTO;
    private Author author;

    @BeforeEach
    void setUp() {
        authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("John Doe");

        author = new Author();
        author.setId(1L);
        author.setName("John Doe");

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Sample Book");
        bookDTO.setDescription("Sample Description");
        bookDTO.setAuthorDTO(authorDTO);

        book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");
        book.setDescription("Sample Description");
        book.setAuthor(author);
    }

    @Test
    void testFindByAuthor() {
        when(authorMapper.mapToEntity(any(AuthorDTO.class))).thenReturn(author);
        when(bookRepository.findByAuthor(any(Author.class))).thenReturn(Arrays.asList(book));
        when(bookMapper.mapToDTO(any(Book.class))).thenReturn(bookDTO);

        List<BookDTO> result = bookService.findByAuthor(authorDTO);

        assertEquals(1, result.size());
        assertEquals(bookDTO, result.get(0));
    }

    @Test
    void testSave() {
        when(bookMapper.mapToEntity(any(BookDTO.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.save(bookDTO);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdate() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(authorMapper.mapToEntity(any(AuthorDTO.class))).thenReturn(author);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.update(bookDTO);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testUpdateEntityNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            bookService.update(bookDTO);
        });
    }

    @Test
    void testDelete() {
        when(bookMapper.mapToEntity(any(BookDTO.class))).thenReturn(book);
        doNothing().when(bookRepository).delete(any(Book.class));

        bookService.delete(bookDTO);

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void testFindById() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.mapToDTO(any(Book.class))).thenReturn(bookDTO);

        BookDTO result = bookService.findById(1L);

        assertEquals(bookDTO, result);
    }

    @Test
    void testFindByIdEntityNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            bookService.findById(1L);
        });
    }

    @Test
    void testFindAll() {
        List<Book> books = Arrays.asList(book);
        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.mapToDTO(any(Book.class))).thenReturn(bookDTO);

        List<BookDTO> result = bookService.findAll();

        assertEquals(1, result.size());
        assertEquals(bookDTO, result.get(0));
    }
}