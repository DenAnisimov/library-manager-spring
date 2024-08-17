package org.example.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BookDTO {
    private Long id;

    private String title;

    private String description;

    private AuthorDTO authorDTO;

    private Set<GenreDTO> genreDTOs;

    public BookDTO() {
        genreDTOs = new HashSet<>();
    }

    public BookDTO(String title, String description, AuthorDTO authorDTO) {
        this.title = title;
        this.description = description;
        this.authorDTO = authorDTO;
        genreDTOs = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AuthorDTO getAuthorDTO() {
        return authorDTO;
    }

    public void setAuthorDTO(AuthorDTO authorDTO) {
        this.authorDTO = authorDTO;
    }

    public Set<GenreDTO> getGenreDTOs() {
        return genreDTOs;
    }

    public void setGenreDTOs(Set<GenreDTO> genreDTOs) {
        this.genreDTOs = genreDTOs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDTO bookDTO)) return false;
        return Objects.equals(id, bookDTO.id) && Objects.equals(title, bookDTO.title) && Objects.equals(description, bookDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", authorDTOId=" + (authorDTO != null ? authorDTO.getId() : "null") +
                ", genreDTOs=" + genreDTOs +
                '}';
    }
}
