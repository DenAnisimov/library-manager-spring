package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.AuthorDTO;
import org.example.service.impl.AuthorService;
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

class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveAuthor() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("John Doe");

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isCreated());

        verify(authorService, times(1)).save(any(AuthorDTO.class));
    }

    @Test
    void updateAuthor() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("John Doe");

        mockMvc.perform(put("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isOk());

        verify(authorService, times(1)).update(any(AuthorDTO.class));
    }

    @Test
    void deleteAuthor() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("John Doe");

        mockMvc.perform(delete("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isNoContent());

        verify(authorService, times(1)).delete(any(AuthorDTO.class));
    }

    @Test
    void getAuthorById() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("John Doe");

        when(authorService.findById(1L)).thenReturn(authorDTO);

        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")));

        verify(authorService, times(1)).findById(1L);
    }

    @Test
    void getAllAuthors() throws Exception {
        AuthorDTO authorDTO1 = new AuthorDTO();
        authorDTO1.setId(1L);
        authorDTO1.setName("John Doe");

        AuthorDTO authorDTO2 = new AuthorDTO();
        authorDTO2.setId(2L);
        authorDTO2.setName("Jane Doe");

        List<AuthorDTO> authorDTOs = Arrays.asList(authorDTO1, authorDTO2);

        when(authorService.findAll()).thenReturn(authorDTOs);

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Jane Doe")));

        verify(authorService, times(1)).findAll();
    }
}