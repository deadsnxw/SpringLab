package org.example.laboratory.repository;

import org.example.laboratory.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    private final Map<Long, Book> books = new HashMap<>();

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public List<Book> findByAuthor(String author) {
        return books.values().stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> findByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    public List<Book> findByKeyword(String keyword) {
        return books.values().stream()
                .filter(book -> book.getKeywords().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void save(Book book) {
        books.put(book.getId(), book);
    }

    public void delete(Long id) {
        books.remove(id);
    }

    public Book findById(Long id) {
        return books.get(id);
    }
}

