package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.AuthorDetailsDTO;
import org.example.entity.AuthorDetails;
import org.example.map.AuthorDetailsMapper;
import org.example.repository.AuthorDetailsRepository;
import org.example.service.ServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorDetailsService implements ServiceDAO<AuthorDetailsDTO> {
    private final AuthorDetailsMapper authorDetailsMapper;
    private final AuthorDetailsRepository authorDetailsRepository;

    @Autowired
    public AuthorDetailsService(AuthorDetailsMapper authorDetailsMapper,
                                AuthorDetailsRepository authorDetailsRepository) {
        this.authorDetailsMapper = authorDetailsMapper;
        this.authorDetailsRepository = authorDetailsRepository;
    }

    @Override
    public void save(AuthorDetailsDTO authorDetailsDTO) throws RuntimeException {
        AuthorDetails authorDetails = authorDetailsMapper.mapToEntity(authorDetailsDTO);
        authorDetailsRepository.save(authorDetails);
    }

    @Override
    public void update(AuthorDetailsDTO authorDetailsDTO) throws RuntimeException {
        AuthorDetails authorDetails = authorDetailsRepository.findById(authorDetailsDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("AuthorDetails not found with id: " + authorDetailsDTO.getId()));
        authorDetails.setBriefBiography(authorDetailsDTO.getBriefBiography());
        authorDetails.setLifeYears(authorDetailsDTO.getLifeYears());
        authorDetailsRepository.save(authorDetails);
    }

    @Override
    public void delete(AuthorDetailsDTO authorDetailsDTO) throws RuntimeException {
        authorDetailsRepository.deleteById(authorDetailsDTO.getId());
    }

    @Override
    public AuthorDetailsDTO findById(Long id) throws RuntimeException {
        AuthorDetails authorDetails = authorDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AuthorDetails not found with id: " + id));
        return authorDetailsMapper.mapToDTO(authorDetails);
    }

    @Override
    public List<AuthorDetailsDTO> findAll() throws RuntimeException {
        List<AuthorDetails> authorDetails = authorDetailsRepository.findAll();
        return authorDetailsMapper.mapToDTO(authorDetails);
    }
}
