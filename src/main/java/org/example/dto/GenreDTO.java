package org.example.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class GenreDTO {
    private Long id;

    private String name;

    private Set<BookDTO> bookDTOs;

    public GenreDTO() {
        bookDTOs = new HashSet<>();
    }

    public GenreDTO(String name) {
        this.name = name;
        bookDTOs = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BookDTO> getBookDTOs() {
        return bookDTOs;
    }

    public void setBookDTOs(Set<BookDTO> bookDTOs) {
        this.bookDTOs = bookDTOs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenreDTO genreDTO)) return false;
        return Objects.equals(id, genreDTO.id) && Objects.equals(name, genreDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "GenreDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", booksDTOs=" + bookDTOs +
                '}';
    }
}
