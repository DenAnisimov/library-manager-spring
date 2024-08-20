package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;
import org.example.service.impl.BookService;
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

class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getBooksByAuthor() throws Exception {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("John Doe");

        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setId(1L);
        bookDTO1.setTitle("Book 1");

        BookDTO bookDTO2 = new BookDTO();
        bookDTO2.setId(2L);
        bookDTO2.setTitle("Book 2");

        List<BookDTO> bookDTOs = Arrays.asList(bookDTO1, bookDTO2);

        when(bookService.findByAuthor(any(AuthorDTO.class))).thenReturn(bookDTOs);

        mockMvc.perform(get("/books/by-author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Book 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Book 2")));

        verify(bookService, times(1)).findByAuthor(any(AuthorDTO.class));
    }

    @Test
    void saveBook() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Book 1");

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated());

        verify(bookService, times(1)).save(any(BookDTO.class));
    }

    @Test
    void updateBook() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Updated Book 1");

        mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk());

        verify(bookService, times(1)).update(any(BookDTO.class));
    }

    @Test
    void deleteBook() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Book 1");

        mockMvc.perform(delete("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).delete(any(BookDTO.class));
    }

    @Test
    void getBookById() throws Exception {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Book 1");

        when(bookService.findById(1L)).thenReturn(bookDTO);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Book 1")));

        verify(bookService, times(1)).findById(1L);
    }

    @Test
    void getAllBooks() throws Exception {
        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setId(1L);
        bookDTO1.setTitle("Book 1");

        BookDTO bookDTO2 = new BookDTO();
        bookDTO2.setId(2L);
        bookDTO2.setTitle("Book 2");

        List<BookDTO> bookDTOs = Arrays.asList(bookDTO1, bookDTO2);

        when(bookService.findAll()).thenReturn(bookDTOs);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Book 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Book 2")));

        verify(bookService, times(1)).findAll();
    }
}