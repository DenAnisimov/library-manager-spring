package org.example.map;

import org.example.dto.BookDTO;
import org.example.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { AuthorMapper.class })
public interface BookMapper {
    @Mapping(source = "author", target = "authorDTO")
    @Mapping(source = "genres", target = "genreDTOs")
    BookDTO mapToDTO(Book book);

    @Mapping(source = "authorDTO", target = "author")
    @Mapping(source = "genreDTOs", target = "genres")
    Book mapToEntity(BookDTO bookDTO);
}
