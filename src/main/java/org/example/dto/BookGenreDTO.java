package org.example.dto;

import java.util.Objects;

public class BookGenreDTO {
    private Long id;

    private BookDTO bookDTO;

    private GenreDTO genreDTO;

    public BookGenreDTO() {
    }

    public BookGenreDTO(BookDTO bookDTO, GenreDTO genreDTO) {
        this.bookDTO = bookDTO;
        this.genreDTO = genreDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookDTO getBookDTO() {
        return bookDTO;
    }

    public void setBookDTO(BookDTO bookDTO) {
        this.bookDTO = bookDTO;
    }

    public GenreDTO getGenreDTO() {
        return genreDTO;
    }

    public void setGenreDTO(GenreDTO genreDTO) {
        this.genreDTO = genreDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookGenreDTO bookGenreDTO)) return false;
        return Objects.equals(id, bookGenreDTO.id) && Objects.equals(bookDTO, bookGenreDTO.bookDTO) && Objects.equals(genreDTO, bookGenreDTO.genreDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookDTO, genreDTO);
    }

    @Override
    public String toString() {
        return "BookGenreDTO{" +
                "id=" + id +
                ", book=" + bookDTO +
                ", genre=" + genreDTO +
                '}';
    }
}
