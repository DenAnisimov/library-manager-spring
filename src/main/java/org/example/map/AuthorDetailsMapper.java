package org.example.map;

import org.example.dto.AuthorDetailsDTO;
import org.example.entity.AuthorDetails;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorDetailsMapper {
    AuthorDetails mapToEntity(AuthorDetailsDTO authorDetailsDTO);
    AuthorDetailsDTO mapToDTO(AuthorDetails authorDetails);
    List<AuthorDetailsDTO> mapToDTO(List<AuthorDetails> authorDetails);
}
