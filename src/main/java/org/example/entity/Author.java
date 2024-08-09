package org.example.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_details_id")
    private AuthorDetails authorDetails;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author() {
    }

    public Author(Long id, String name, AuthorDetails authorDetails) {
        this.id = id;
        this.name = name;
        this.authorDetails = authorDetails;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorDetails getAuthorDetails() {
        return authorDetails;
    }

    public void setAuthorDetails(AuthorDetails authorDetails) {
        this.authorDetails = authorDetails;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return Objects.equals(id, author.id) && Objects.equals(name, author.name) && Objects.equals(authorDetails, author.authorDetails) && Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authorDetails, books);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorDetails=" + authorDetails +
                ", books=" + books +
                '}';
    }
}
