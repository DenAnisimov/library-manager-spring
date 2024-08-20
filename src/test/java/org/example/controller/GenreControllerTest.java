package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.GenreDTO;
import org.example.service.impl.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GenreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GenreService genreService;

    @InjectMocks
    private GenreController genreController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveGenre() throws Exception {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setName("Fantasy");

        mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDTO)))
                .andExpect(status().isCreated());

        verify(genreService, times(1)).save(any(GenreDTO.class));
    }

    @Test
    void updateGenre() throws Exception {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(1L);
        genreDTO.setName("Fantasy");

        mockMvc.perform(put("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDTO)))
                .andExpect(status().isOk());

        verify(genreService, times(1)).update(any(GenreDTO.class));
    }

    @Test
    void deleteGenre() throws Exception {
        mockMvc.perform(delete("/genres/1"))
                .andExpect(status().isNoContent());

        verify(genreService, times(1)).delete(any(GenreDTO.class));
    }

    @Test
    void getGenreById() throws Exception {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(1L);
        genreDTO.setName("Fantasy");

        when(genreService.findById(1L)).thenReturn(genreDTO);

        mockMvc.perform(get("/genres/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Fantasy")));

        verify(genreService, times(1)).findById(1L);
    }

    @Test
    void getAllGenres() throws Exception {
        GenreDTO genreDTO1 = new GenreDTO();
        genreDTO1.setId(1L);
        genreDTO1.setName("Fantasy");

        GenreDTO genreDTO2 = new GenreDTO();
        genreDTO2.setId(2L);
        genreDTO2.setName("Science Fiction");

        List<GenreDTO> genreDTOs = Arrays.asList(genreDTO1, genreDTO2);

        when(genreService.findAll()).thenReturn(genreDTOs);

        mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Fantasy")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Science Fiction")));

        verify(genreService, times(1)).findAll();
    }
}