package org.example.map;

import org.example.dto.AuthorDTO;
import org.example.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AuthorDetailsMapper.class, BookMapper.class })
public interface AuthorMapper {
    @Mapping(source = "authorDetails", target = "authorDetailsDTO")
    @Mapping(source = "books", target = "books")
    AuthorDTO mapToDTO(Author author);

    @Mapping(source = "authorDetailsDTO", target = "authorDetails")
    @Mapping(source = "books", target = "books")
    Author mapToEntity(AuthorDTO authorDTO);

    List<AuthorDTO> mapToDTO(List<Author> authors);
}
