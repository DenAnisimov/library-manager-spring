package org.example.service;

import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;

import java.util.List;

public interface BookServiceDAO extends ServiceDAO<BookDTO> {
    List<BookDTO> findByAuthor(AuthorDTO authorDTO) throws RuntimeException;
}
