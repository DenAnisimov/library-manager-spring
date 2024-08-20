package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AuthorDetailsDTO;
import org.example.service.impl.AuthorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors_details")
public class AuthorDetailsController {
    private final AuthorDetailsService authorDetailsService;

    @Autowired
    public AuthorDetailsController(AuthorDetailsService authorDetailsService) {
        this.authorDetailsService = authorDetailsService;
    }

    @PostMapping
    public ResponseEntity<Void> saveAuthorDetails(@RequestBody AuthorDetailsDTO authorDetailsDTO) {
        authorDetailsService.save(authorDetailsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateAuthorDetails(@RequestBody AuthorDetailsDTO authorDetailsDTO) {
        try {
            authorDetailsService.update(authorDetailsDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAuthorDetails(@RequestBody AuthorDetailsDTO authorDetailsDTO) {

        try {
            authorDetailsService.delete(authorDetailsDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDetailsDTO> getAuthorDetailsById(@PathVariable Long id) {
        try {
            AuthorDetailsDTO authorDetailsDTO = authorDetailsService.findById(id);
            return new ResponseEntity<>(authorDetailsDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDetailsDTO>> getAllAuthorDetails() {
        List<AuthorDetailsDTO> authorDetailsDTOs = authorDetailsService.findAll();
        return new ResponseEntity<>(authorDetailsDTOs, HttpStatus.OK);
    }
}