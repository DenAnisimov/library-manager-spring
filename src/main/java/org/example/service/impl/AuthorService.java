package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.example.map.AuthorMapper;
import org.example.repository.AuthorRepository;
import org.example.service.ServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService implements ServiceDAO<AuthorDTO> {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public void save(AuthorDTO authorDTO) throws RuntimeException {
        Author author = authorMapper.mapToEntity(authorDTO);
        authorRepository.save(author);
    }

    @Override
    public void update(AuthorDTO authorDTO) throws RuntimeException {
        Author author = authorRepository.findById(authorDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + authorDTO.getId()));
        author.setName(authorDTO.getName());
        authorRepository.save(author);
    }

    @Override
    public void delete(AuthorDTO authorDTO) throws RuntimeException {
        authorRepository.deleteById(authorDTO.getId());
    }

    @Override
    public AuthorDTO findById(Long id) throws RuntimeException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        return authorMapper.mapToDTO(author);
    }

    @Override
    public List<AuthorDTO> findAll() throws RuntimeException {
        List<Author> authors = authorRepository.findAll();
        return authorMapper.mapToDTO(authors);
    }
}
