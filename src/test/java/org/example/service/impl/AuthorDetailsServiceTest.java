package org.example.service.impl;

import org.example.dto.AuthorDetailsDTO;
import org.example.entity.AuthorDetails;
import org.example.map.AuthorDetailsMapper;
import org.example.repository.AuthorDetailsRepository;
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
class AuthorDetailsServiceTest {

    @Mock
    private AuthorDetailsMapper authorDetailsMapper;

    @Mock
    private AuthorDetailsRepository authorDetailsRepository;

    @InjectMocks
    private AuthorDetailsService authorDetailsService;

    private AuthorDetailsDTO authorDetailsDTO;
    private AuthorDetails authorDetails;

    @BeforeEach
    public void setUp() {
        authorDetailsDTO = new AuthorDetailsDTO();
        authorDetailsDTO.setId(1L);
        authorDetailsDTO.setBriefBiography("Biography");
        authorDetailsDTO.setLifeYears("1900-1950");

        authorDetails = new AuthorDetails();
        authorDetails.setId(1L);
        authorDetails.setBriefBiography("Biography");
        authorDetails.setLifeYears("1900-1950");
    }

    @Test
    void testSave() {
        when(authorDetailsMapper.mapToEntity(any(AuthorDetailsDTO.class))).thenReturn(authorDetails);
        when(authorDetailsRepository.save(any(AuthorDetails.class))).thenReturn(authorDetails);

        authorDetailsService.save(authorDetailsDTO);

        verify(authorDetailsRepository, times(1)).save(authorDetails);
    }

    @Test
    void testUpdate() {
        when(authorDetailsRepository.findById(anyLong())).thenReturn(Optional.of(authorDetails));
        when(authorDetailsRepository.save(any(AuthorDetails.class))).thenReturn(authorDetails);

        authorDetailsService.update(authorDetailsDTO);

        verify(authorDetailsRepository, times(1)).save(authorDetails);
    }

    @Test
    void testUpdateEntityNotFound() {
        when(authorDetailsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            authorDetailsService.update(authorDetailsDTO);
        });
    }

    @Test
    void testDelete() {
        doNothing().when(authorDetailsRepository).deleteById(anyLong());

        authorDetailsService.delete(authorDetailsDTO);

        verify(authorDetailsRepository, times(1)).deleteById(authorDetailsDTO.getId());
    }

    @Test
    void testFindById() {
        when(authorDetailsRepository.findById(anyLong())).thenReturn(Optional.of(authorDetails));
        when(authorDetailsMapper.mapToDTO(any(AuthorDetails.class))).thenReturn(authorDetailsDTO);

        AuthorDetailsDTO result = authorDetailsService.findById(1L);

        assertEquals(authorDetailsDTO, result);
    }

    @Test
    void testFindByIdEntityNotFound() {
        when(authorDetailsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            authorDetailsService.findById(1L);
        });
    }

    @Test
    void testFindAll() {
        List<AuthorDetails> authorDetailsList = Arrays.asList(authorDetails);
        when(authorDetailsRepository.findAll()).thenReturn(authorDetailsList);
        when(authorDetailsMapper.mapToDTO(anyList())).thenReturn(Arrays.asList(authorDetailsDTO));

        List<AuthorDetailsDTO> result = authorDetailsService.findAll();

        assertEquals(1, result.size());
        assertEquals(authorDetailsDTO, result.get(0));
    }
}