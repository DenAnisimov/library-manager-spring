package org.example.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "author_details")
public class AuthorDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "life_years")
    private String lifeYears;

    @Column(name = "brief_biography")
    private String briefBiography;

    public AuthorDetails() {
    }

    public AuthorDetails(String lifeYears, String briefBiography) {
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
        if (!(o instanceof AuthorDetails that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(lifeYears, that.lifeYears) && Objects.equals(briefBiography, that.briefBiography);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lifeYears, briefBiography);
    }

    @Override
    public String toString() {
        return "AuthorDetails{" +
                "id=" + id +
                ", lifeYears='" + lifeYears + '\'' +
                ", briefBiography='" + briefBiography + '\'' +
                '}';
    }
}
