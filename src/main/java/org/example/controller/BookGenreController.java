package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.BookGenreDTO;
import org.example.service.impl.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-genres")
public class BookGenreController {
    private final BookGenreService bookGenreService;

    @Autowired
    public BookGenreController(BookGenreService bookGenreService) {
        this.bookGenreService = bookGenreService;
    }

    @GetMapping
    public ResponseEntity<List<BookGenreDTO>> getAllBookGenres() {
        List<BookGenreDTO> bookGenreDTOs = bookGenreService.findAll();
        return new ResponseEntity<>(bookGenreDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookGenreDTO> getBookGenreById(@PathVariable Long id) {
        try {
            BookGenreDTO bookGenreDTO = bookGenreService.findById(id);
            return new ResponseEntity<>(bookGenreDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> saveBookGenre(@RequestBody BookGenreDTO bookGenreDTO) {
        bookGenreService.save(bookGenreDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBookGenre(@RequestBody BookGenreDTO bookGenreDTO) {
        try {
            bookGenreService.delete(bookGenreDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}