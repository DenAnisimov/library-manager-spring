package org.example.service;

import java.util.List;

public interface ServiceDAO<T> {
    void save(T t) throws RuntimeException;
    void update(T t) throws RuntimeException;
    void delete(T t) throws RuntimeException;
    T findById(Long id) throws RuntimeException;
    List<T> findAll() throws RuntimeException;
}
