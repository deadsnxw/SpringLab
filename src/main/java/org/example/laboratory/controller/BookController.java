package org.example.laboratory.controller;

import org.springframework.ui.Model;
import org.example.laboratory.model.Book;
import org.example.laboratory.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String listBooksForGuest(Model model,
                                    @RequestParam(value = "searchType", required = false) String searchType,
                                    @RequestParam(value = "query", required = false) String query) {
        List<Book> books = bookService.getAllBooks();

        if (searchType != null && query != null) {
            switch (searchType) {
                case "author":
                    books = bookService.searchByAuthor(query);
                    break;
                case "title":
                    books = bookService.searchByTitle(query);
                    break;
                case "keywords":
                    books = bookService.searchByKeyword(query);
                    break;
            }
        }

        model.addAttribute("books", books);
        model.addAttribute("query", query);
        model.addAttribute("searchType", searchType);
        return "guest/books-list";
    }

    // admin
    @GetMapping("/admin")
    public String listBooksForAdmin(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "admin/books-list";
    }

    @GetMapping("/admin/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "admin/book-form";
    }

    @PostMapping("/admin/save")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.addOrUpdateBook(book);
        return "redirect:/books/admin";
    }

    @GetMapping("/admin/edit")
    public String showEditBookForm(@RequestParam("id") Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "admin/book-form";
    }

    @GetMapping("/admin/delete")
    public String deleteBook(@RequestParam("id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/books/admin";
    }
}


