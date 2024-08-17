package org.example.service;

import org.example.dto.BookGenreDTO;

import java.util.List;

public interface BookGenreServiceDAO {
    List<BookGenreDTO> findAll();
    BookGenreDTO findById(Long id);
    void save(BookGenreDTO bookGenreDTO);
    void delete(BookGenreDTO bookGenreDTO);
}
