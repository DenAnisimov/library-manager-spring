package org.example.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "book_genre", schema = "public")
public class BookGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    public BookGenre() {
    }

    public BookGenre(Book book, Genre genre) {
        this.book = book;
        this.genre = genre;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookGenre bookGenre)) return false;
        return Objects.equals(id, bookGenre.id) && Objects.equals(book, bookGenre.book) && Objects.equals(genre, bookGenre.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, book, genre);
    }

    @Override
    public String toString() {
        return "BookGenre{" +
                "id=" + id +
                ", book=" + book +
                ", genre=" + genre +
                '}';
    }
}