package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.dto.BookDTO;
import org.example.dto.BookGenreDTO;
import org.example.dto.GenreDTO;
import org.example.service.impl.BookGenreService;
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

class BookGenreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookGenreService bookGenreService;

    @InjectMocks
    private BookGenreController bookGenreController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookGenreController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllBookGenres() throws Exception {
        BookGenreDTO bookGenreDTO1 = new BookGenreDTO();
        bookGenreDTO1.setId(1L);
        bookGenreDTO1.setBookDTO(new BookDTO());
        bookGenreDTO1.setGenreDTO(new GenreDTO());

        BookGenreDTO bookGenreDTO2 = new BookGenreDTO();
        bookGenreDTO2.setId(2L);
        bookGenreDTO2.setBookDTO(new BookDTO());
        bookGenreDTO2.setGenreDTO(new GenreDTO());

        List<BookGenreDTO> bookGenreDTOs = Arrays.asList(bookGenreDTO1, bookGenreDTO2);

        when(bookGenreService.findAll()).thenReturn(bookGenreDTOs);

        mockMvc.perform(get("/book-genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));

        verify(bookGenreService, times(1)).findAll();
    }

    @Test
    void getBookGenreById() throws Exception {
        BookGenreDTO bookGenreDTO = new BookGenreDTO();
        bookGenreDTO.setId(1L);
        bookGenreDTO.setBookDTO(new BookDTO());
        bookGenreDTO.setGenreDTO(new GenreDTO());

        when(bookGenreService.findById(1L)).thenReturn(bookGenreDTO);

        mockMvc.perform(get("/book-genres/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(bookGenreService, times(1)).findById(1L);
    }

    @Test
    void getBookGenreByIdNotFound() throws Exception {
        when(bookGenreService.findById(1L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/book-genres/1"))
                .andExpect(status().isNotFound());

        verify(bookGenreService, times(1)).findById(1L);
    }

    @Test
    void saveBookGenre() throws Exception {
        BookGenreDTO bookGenreDTO = new BookGenreDTO();
        bookGenreDTO.setId(1L);
        bookGenreDTO.setBookDTO(new BookDTO());
        bookGenreDTO.setGenreDTO(new GenreDTO());

        mockMvc.perform(post("/book-genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookGenreDTO)))
                .andExpect(status().isCreated());

        verify(bookGenreService, times(1)).save(any(BookGenreDTO.class));
    }

    @Test
    void deleteBookGenre() throws Exception {
        BookGenreDTO bookGenreDTO = new BookGenreDTO();
        bookGenreDTO.setId(1L);
        bookGenreDTO.setBookDTO(new BookDTO());
        bookGenreDTO.setGenreDTO(new GenreDTO());

        mockMvc.perform(delete("/book-genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookGenreDTO)))
                .andExpect(status().isNoContent());

        verify(bookGenreService, times(1)).delete(any(BookGenreDTO.class));
    }

    @Test
    void deleteBookGenreNotFound() throws Exception {
        BookGenreDTO bookGenreDTO = new BookGenreDTO();
        bookGenreDTO.setId(1L);
        bookGenreDTO.setBookDTO(new BookDTO());
        bookGenreDTO.setGenreDTO(new GenreDTO());

        doThrow(new EntityNotFoundException()).when(bookGenreService).delete(any(BookGenreDTO.class));

        mockMvc.perform(delete("/book-genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookGenreDTO)))
                .andExpect(status().isNotFound());

        verify(bookGenreService, times(1)).delete(any(BookGenreDTO.class));
    }
}