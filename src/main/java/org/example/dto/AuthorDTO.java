package org.example.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AuthorDTO {
    private Long id;

    private String name;

    private AuthorDetailsDTO authorDetailsDTO;

    private List<BookDTO> books;

    public AuthorDTO() {
        books = new ArrayList<>();
    }

    public AuthorDTO(String name, AuthorDetailsDTO authorDetails) {
        this.name = name;
        this.authorDetailsDTO = authorDetails;
        books = new ArrayList<>();
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

    public AuthorDetailsDTO getAuthorDetailsDTO() {
        return authorDetailsDTO;
    }

    public void setAuthorDetailsDTO(AuthorDetailsDTO authorDetailsDTO) {
        this.authorDetailsDTO = authorDetailsDTO;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorDTO author)) return false;
        return Objects.equals(id, author.id) && Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorDetailsDTO=" + (authorDetailsDTO != null ? authorDetailsDTO.toString() : "null") +
                ", books=" + books +
                '}';
    }
}
