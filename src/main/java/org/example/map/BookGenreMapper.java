package org.example.map;

import org.example.dto.BookGenreDTO;
import org.example.entity.BookGenre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { BookGenreMapper.class, GenreMapper.class })
public interface BookGenreMapper {
    @Mapping(source = "genreDTO", target = "genre")
    @Mapping(source = "bookDTO", target = "book")
    BookGenre mapToEntity(BookGenreDTO bookGenreDTO);
    @Mapping(source = "genre", target = "genreDTO")
    @Mapping(source = "book", target = "bookDTO")
    BookGenreDTO mapToDTO(BookGenre bookGenre);
    List<BookGenreDTO> mapToDTO(List<BookGenre> bookGenres);
}
