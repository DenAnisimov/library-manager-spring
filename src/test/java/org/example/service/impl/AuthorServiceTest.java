package org.example.service.impl;

import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.example.map.AuthorMapper;
import org.example.repository.AuthorRepository;
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
class AuthorServiceTest {

    @Mock
    private AuthorMapper authorMapper;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

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
    }

    @Test
    void testSave() {
        when(authorMapper.mapToEntity(any(AuthorDTO.class))).thenReturn(author);
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        authorService.save(authorDTO);

        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testUpdate() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        authorService.update(authorDTO);

        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testUpdateEntityNotFound() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            authorService.update(authorDTO);
        });
    }

    @Test
    void testDelete() {
        doNothing().when(authorRepository).deleteById(anyLong());

        authorService.delete(authorDTO);

        verify(authorRepository, times(1)).deleteById(authorDTO.getId());
    }

    @Test
    void testFindById() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(authorMapper.mapToDTO(any(Author.class))).thenReturn(authorDTO);

        AuthorDTO result = authorService.findById(1L);

        assertEquals(authorDTO, result);
    }

    @Test
    void testFindByIdEntityNotFound() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            authorService.findById(1L);
        });
    }

    @Test
    void testFindAll() {
        List<Author> authors = Arrays.asList(author);
        when(authorRepository.findAll()).thenReturn(authors);
        when(authorMapper.mapToDTO(anyList())).thenReturn(Arrays.asList(authorDTO));

        List<AuthorDTO> result = authorService.findAll();

        assertEquals(1, result.size());
        assertEquals(authorDTO, result.get(0));
    }
}