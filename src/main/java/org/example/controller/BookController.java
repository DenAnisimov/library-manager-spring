package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;
import org.example.service.impl.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/by-author")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@RequestBody AuthorDTO authorDTO) {
        try {
            List<BookDTO> bookDTOs = bookService.findByAuthor(authorDTO);
            return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Void> saveBook(@RequestBody BookDTO bookDTO) {
        bookService.save(bookDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateBook(@RequestBody BookDTO bookDTO) {
        try {
            bookService.update(bookDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBook(@RequestBody BookDTO bookDTO) {
        try {
            bookService.delete(bookDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        try {
            BookDTO bookDTO = bookService.findById(id);
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> bookDTOs = bookService.findAll();
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }
}