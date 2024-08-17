package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.GenreDTO;
import org.example.entity.Genre;
import org.example.map.GenreMapper;
import org.example.repository.GenreRepository;
import org.example.service.ServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService implements ServiceDAO<GenreDTO> {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreService(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    @Override
    public void save(GenreDTO genreDTO) throws RuntimeException {
        Genre genre = genreMapper.mapToEntity(genreDTO);
        genreRepository.save(genre);
    }

    @Override
    public void update(GenreDTO genreDTO) throws RuntimeException {
        Genre genre = genreRepository.findById(genreDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Genre with id " + genreDTO.getId() + " not found"));
        genre.setName(genreDTO.getName());
        genreRepository.save(genre);
    }

    @Override
    public void delete(GenreDTO genreDTO) throws RuntimeException {
        genreRepository.deleteById(genreDTO.getId());
    }

    @Override
    public GenreDTO findById(Long id) throws RuntimeException {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id " + id + " not found"));
        return genreMapper.mapToDTO(genre);
    }

    @Override
    public List<GenreDTO> findAll() throws RuntimeException {
        List<Genre> genres = genreRepository.findAll();
        return genreMapper.mapToDTO(genres);
    }
}
