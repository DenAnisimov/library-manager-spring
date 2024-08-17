package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AuthorDTO;
import org.example.dto.BookDTO;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.map.AuthorMapper;
import org.example.map.BookMapper;
import org.example.repository.BookRepository;
import org.example.service.BookServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService implements BookServiceDAO {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public BookService(BookMapper bookMapper, BookRepository bookRepository, AuthorMapper authorMapper) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<BookDTO> findByAuthor(AuthorDTO authorDTO) throws RuntimeException {
        Author author = authorMapper.mapToEntity(authorDTO);
        List<Book> books = bookRepository.findByAuthor(author);
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                bookDTOs.add(bookMapper.mapToDTO(book));
            }
        }
        return bookDTOs;
    }

    @Override
    public void save(BookDTO bookDTO) throws RuntimeException {
        Book book = bookMapper.mapToEntity(bookDTO);
        bookRepository.save(book);
    }

    @Override
    public void update(BookDTO bookDTO) throws RuntimeException {
        Book book = bookRepository.findById(bookDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookDTO.getId()));
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(authorMapper.mapToEntity(bookDTO.getAuthorDTO()));
        book.setDescription(bookDTO.getDescription());
        bookRepository.save(book);
    }

    @Override
    public void delete(BookDTO bookDTO) throws RuntimeException {
        bookRepository.delete(bookMapper.mapToEntity(bookDTO));
    }

    @Override
    public BookDTO findById(Long id) throws RuntimeException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        return bookMapper.mapToDTO(book);
    }

    @Override
    public List<BookDTO> findAll() throws RuntimeException {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = new ArrayList<>();
        for (Book book : books) {
            bookDTOs.add(bookMapper.mapToDTO(book));
        }
        return bookDTOs;
    }
}
