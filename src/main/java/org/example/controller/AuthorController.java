package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AuthorDTO;
import org.example.service.impl.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Void> saveAuthor(@RequestBody AuthorDTO authorDTO) {
        authorService.save(authorDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateAuthor(@RequestBody AuthorDTO authorDTO) {
        try {
            authorService.update(authorDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAuthor(@RequestBody AuthorDTO authorDTO) {
        try {
            authorService.delete(authorDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        try {
            AuthorDTO authorDTO = authorService.findById(id);
            return new ResponseEntity<>(authorDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authorDTOs = authorService.findAll();
        return new ResponseEntity<>(authorDTOs, HttpStatus.OK);
    }
}