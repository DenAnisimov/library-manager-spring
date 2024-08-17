package org.example.service.impl;

import org.example.dto.GenreDTO;
import org.example.entity.Genre;
import org.example.map.GenreMapper;
import org.example.repository.GenreRepository;
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
class GenreServiceTest {

    @Mock
    private GenreMapper genreMapper;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    private GenreDTO genreDTO;
    private Genre genre;

    @BeforeEach
    void setUp() {
        genreDTO = new GenreDTO();
        genreDTO.setId(1L);
        genreDTO.setName("Science Fiction");

        genre = new Genre();
        genre.setId(1L);
        genre.setName("Science Fiction");
    }

    @Test
    void testSave() {
        when(genreMapper.mapToEntity(any(GenreDTO.class))).thenReturn(genre);
        when(genreRepository.save(any(Genre.class))).thenReturn(genre);

        genreService.save(genreDTO);

        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    void testUpdate() {
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(genre));
        when(genreRepository.save(any(Genre.class))).thenReturn(genre);

        genreService.update(genreDTO);

        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    void testUpdateEntityNotFound() {
        when(genreRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            genreService.update(genreDTO);
        });
    }

    @Test
    void testDelete() {
        doNothing().when(genreRepository).deleteById(anyLong());

        genreService.delete(genreDTO);

        verify(genreRepository, times(1)).deleteById(genreDTO.getId());
    }

    @Test
    void testFindById() {
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(genre));
        when(genreMapper.mapToDTO(any(Genre.class))).thenReturn(genreDTO);

        GenreDTO result = genreService.findById(1L);

        assertEquals(genreDTO, result);
    }

    @Test
    void testFindByIdEntityNotFound() {
        when(genreRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            genreService.findById(1L);
        });
    }

    @Test
    void testFindAll() {
        List<Genre> genres = Arrays.asList(genre);
        when(genreRepository.findAll()).thenReturn(genres);
        when(genreMapper.mapToDTO(anyList())).thenReturn(Arrays.asList(genreDTO));

        List<GenreDTO> result = genreService.findAll();

        assertEquals(1, result.size());
        assertEquals(genreDTO, result.get(0));
    }
}