package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AuthorDetailsDTO;
import org.example.service.impl.AuthorDetailsService;
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

class AuthorDetailsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorDetailsService authorDetailsService;

    @InjectMocks
    private AuthorDetailsController authorDetailsController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorDetailsController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveAuthorDetails() throws Exception {
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO();
        authorDetailsDTO.setId(1L);
        authorDetailsDTO.setBriefBiography("John Doe's bio");
        authorDetailsDTO.setLifeYears("1950-2020");

        mockMvc.perform(post("/authors_details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDetailsDTO)))
                .andExpect(status().isCreated());

        verify(authorDetailsService, times(1)).save(any(AuthorDetailsDTO.class));
    }

    @Test
    void updateAuthorDetails() throws Exception {
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO();
        authorDetailsDTO.setId(1L);
        authorDetailsDTO.setBriefBiography("John Doe's updated bio");
        authorDetailsDTO.setLifeYears("1950-2020");

        mockMvc.perform(put("/authors_details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDetailsDTO)))
                .andExpect(status().isOk());

        verify(authorDetailsService, times(1)).update(any(AuthorDetailsDTO.class));
    }

    @Test
    void updateAuthorDetailsNotFound() throws Exception {
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO();
        authorDetailsDTO.setId(1L);
        authorDetailsDTO.setBriefBiography("John Doe's updated bio");
        authorDetailsDTO.setLifeYears("1950-2020");

        doThrow(new EntityNotFoundException()).when(authorDetailsService).update(any(AuthorDetailsDTO.class));

        mockMvc.perform(put("/authors_details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDetailsDTO)))
                .andExpect(status().isNotFound());

        verify(authorDetailsService, times(1)).update(any(AuthorDetailsDTO.class));
    }

    @Test
    void deleteAuthorDetails() throws Exception {
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO();
        authorDetailsDTO.setId(1L);
        authorDetailsDTO.setBriefBiography("John Doe's bio");
        authorDetailsDTO.setLifeYears("1950-2020");

        mockMvc.perform(delete("/authors_details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDetailsDTO)))
                .andExpect(status().isOk());

        verify(authorDetailsService, times(1)).delete(any(AuthorDetailsDTO.class));
    }

    @Test
    void deleteAuthorDetailsNotFound() throws Exception {
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO();
        authorDetailsDTO.setId(1L);
        authorDetailsDTO.setBriefBiography("John Doe's bio");
        authorDetailsDTO.setLifeYears("1950-2020");

        doThrow(new EntityNotFoundException()).when(authorDetailsService).delete(any(AuthorDetailsDTO.class));

        mockMvc.perform(delete("/authors_details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDetailsDTO)))
                .andExpect(status().isNotFound());

        verify(authorDetailsService, times(1)).delete(any(AuthorDetailsDTO.class));
    }

    @Test
    void getAuthorDetailsById() throws Exception {
        AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO();
        authorDetailsDTO.setId(1L);
        authorDetailsDTO.setBriefBiography("John Doe's briefBiography");
        authorDetailsDTO.setLifeYears("1950-2020");

        when(authorDetailsService.findById(1L)).thenReturn(authorDetailsDTO);

        mockMvc.perform(get("/authors_details/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.briefBiography", is("John Doe's briefBiography")))
                .andExpect(jsonPath("$.lifeYears", is("1950-2020")));

        verify(authorDetailsService, times(1)).findById(1L);
    }

    @Test
    void getAuthorDetailsByIdNotFound() throws Exception {
        when(authorDetailsService.findById(1L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/authors_details/1"))
                .andExpect(status().isNotFound());

        verify(authorDetailsService, times(1)).findById(1L);
    }

    @Test
    void getAllAuthorDetails() throws Exception {
        AuthorDetailsDTO authorDetailsDTO1 = new AuthorDetailsDTO();
        authorDetailsDTO1.setId(1L);
        authorDetailsDTO1.setBriefBiography("John Doe's briefBiography");
        authorDetailsDTO1.setLifeYears("1950-2020");

        AuthorDetailsDTO authorDetailsDTO2 = new AuthorDetailsDTO();
        authorDetailsDTO2.setId(2L);
        authorDetailsDTO2.setBriefBiography("Jane Doe's briefBiography");
        authorDetailsDTO2.setLifeYears("1950-2030");

        List<AuthorDetailsDTO> authorDetailsDTOs = Arrays.asList(authorDetailsDTO1, authorDetailsDTO2);

        when(authorDetailsService.findAll()).thenReturn(authorDetailsDTOs);

        mockMvc.perform(get("/authors_details"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].briefBiography", is("John Doe's briefBiography")))
                .andExpect(jsonPath("$[0].lifeYears", is("1950-2020")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].briefBiography", is("Jane Doe's briefBiography")))
                .andExpect(jsonPath("$[1].lifeYears", is("1950-2030")));

        verify(authorDetailsService, times(1)).findAll();
    }
}