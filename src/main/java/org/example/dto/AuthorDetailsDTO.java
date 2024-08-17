package org.example.dto;

import java.util.Objects;

public class AuthorDetailsDTO {
    private Long id;

    private String lifeYears;

    private String briefBiography;

    public AuthorDetailsDTO() {
    }

    public AuthorDetailsDTO(String lifeYears, String briefBiography) {
        this.lifeYears = lifeYears;
        this.briefBiography = briefBiography;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLifeYears() {
        return lifeYears;
    }

    public void setLifeYears(String lifeYears) {
        this.lifeYears = lifeYears;
    }

    public String getBriefBiography() {
        return briefBiography;
    }

    public void setBriefBiography(String briefBiography) {
        this.briefBiography = briefBiography;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorDetailsDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(lifeYears, that.lifeYears) && Objects.equals(briefBiography, that.briefBiography);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lifeYears, briefBiography);
    }

    @Override
    public String toString() {
        return "AuthorDetailsDTO{" +
                "id=" + id +
                ", lifeYears='" + lifeYears + '\'' +
                ", briefBiography='" + briefBiography + '\'' +
                '}';
    }
}
