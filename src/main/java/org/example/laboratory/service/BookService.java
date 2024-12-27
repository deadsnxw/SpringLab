package org.example.laboratory.service;

import org.example.laboratory.model.Book;
import org.example.laboratory.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> searchByKeyword(String keyword) {
        return bookRepository.findByKeyword(keyword);
    }

    public void addOrUpdateBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id);
    }
}

