package org.example.laboratory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.laboratory.model.Book;
import org.example.laboratory.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Create
    @Operation(summary = "Створити книгу", description = "Додати нову книгу до каталогу")
    @ApiResponse(responseCode = "201", description = "Книга успішно створена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        bookService.addOrUpdateBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    // Read all with filtering and pagination
    @Operation(summary = "Отримати список книг",
            description = "Отримати всі книги з можливістю фільтрації за автором, назвою або ключовими словами, а також з підтримкою пагінації.")
    @ApiResponse(responseCode = "200", description = "Список книг",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(
            @Parameter(description = "Фільтр за автором") @RequestParam(value = "author", required = false) String author,
            @Parameter(description = "Фільтр за назвою") @RequestParam(value = "title", required = false) String title,
            @Parameter(description = "Фільтр за ключовими словами") @RequestParam(value = "keyword", required = false) String keyword,
            @Parameter(description = "Номер сторінки (починається з 0)") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Розмір сторінки") @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        List<Book> books = bookService.getAllBooks();

        if (author != null) books = bookService.searchByAuthor(author);
        else if (title != null) books = bookService.searchByTitle(title);
        else if (keyword != null) books = bookService.searchByKeyword(keyword);

        // Поки пагінація не реалізована, просто повертаємо книги
        return ResponseEntity.ok(books);
    }

    // Read by ID
    @Operation(summary = "Отримати книгу за ID", description = "Отримати деталі книги за її унікальним ідентифікатором")
    @ApiResponse(responseCode = "200", description = "Книга знайдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @ApiResponse(responseCode = "404", description = "Книга не знайдена")
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.findById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(book);
    }

    // Update
    @Operation(summary = "Оновити книгу", description = "Оновити інформацію про книгу за її ідентифікатором")
    @ApiResponse(responseCode = "200", description = "Книга успішно оновлена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @ApiResponse(responseCode = "404", description = "Книга не знайдена")
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book book = bookService.findById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        updatedBook.setId(id);
        bookService.addOrUpdateBook(updatedBook);
        return ResponseEntity.ok(updatedBook);
    }

    // Delete
    @Operation(summary = "Видалити книгу", description = "Видалити книгу за її унікальним ідентифікатором")
    @ApiResponse(responseCode = "204", description = "Книга успішно видалена")
    @ApiResponse(responseCode = "404", description = "Книга не знайдена")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book book = bookService.findById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
