package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.BookGenreDTO;
import org.example.entity.BookGenre;
import org.example.map.BookGenreMapper;
import org.example.repository.BookGenreRepository;
import org.example.service.BookGenreServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookGenreService implements BookGenreServiceDAO {
    private final BookGenreRepository bookGenreRepository;
    private final BookGenreMapper bookGenreMapper;

    @Autowired
    public BookGenreService(BookGenreRepository bookGenreRepository,
                            BookGenreMapper bookGenreMapper) {
        this.bookGenreRepository = bookGenreRepository;
        this.bookGenreMapper = bookGenreMapper;
    }

    @Override
    public List<BookGenreDTO> findAll() {
        List<BookGenre> bookGenres = bookGenreRepository.findAll();
        return bookGenreMapper.mapToDTO(bookGenres);
    }

    @Override
    public BookGenreDTO findById(Long id) {
        BookGenre bookGenre = bookGenreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BookGenre not found with id: " + id));
        return bookGenreMapper.mapToDTO(bookGenre);
    }

    @Override
    public void save(BookGenreDTO bookGenreDTO) {
        BookGenre bookGenre = bookGenreMapper.mapToEntity(bookGenreDTO);
        bookGenreRepository.save(bookGenre);
    }

    @Override
    public void delete(BookGenreDTO bookGenreDTO) {
        bookGenreRepository.delete(bookGenreMapper.mapToEntity(bookGenreDTO));
    }
}
