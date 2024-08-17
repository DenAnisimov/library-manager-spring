package org.example.map;

import org.example.dto.GenreDTO;
import org.example.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { BookMapper.class })
public interface GenreMapper {
    @Mapping(source = "bookDTOs", target = "books")
    Genre mapToEntity(GenreDTO genreDTO);
    @Mapping(source = "books", target = "bookDTOs")
    GenreDTO mapToDTO(Genre genre);
    List<GenreDTO> mapToDTO(List<Genre> genres);
}
