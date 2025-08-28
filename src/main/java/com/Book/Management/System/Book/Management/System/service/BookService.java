package com.Book.Management.System.Book.Management.System.service;

import com.Book.Management.System.Book.Management.System.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final List<Book> books = new ArrayList<>();

    // Initialize with some sample data
    static {
        books.add(new Book(1, "The Lord of the Rings", "J.R.R. Tolkien", 45.50));
        books.add(new Book(2, "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", 25.00));
        books.add(new Book(3, "To Kill a Mockingbird", "Harper Lee", 30.75));
        books.add(new Book(4, "The Hobbit", "J.R.R. Tolkien", 40.00));
    }

    private static Integer nextId = books.size() + 1;

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(Integer id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public Book addBook(Book newBook) {
        newBook.setId(nextId++);
        books.add(newBook);
        return newBook;
    }

    public Optional<Book> updateBook(Integer id, Book updatedBook) {
        Optional<Book> existingBook = getBookById(id);
        if (existingBook.isPresent()) {
            Book bookToUpdate = existingBook.get();
            bookToUpdate.setTitle(updatedBook.getTitle());
            bookToUpdate.setAuthor(updatedBook.getAuthor());
            bookToUpdate.setPrice(updatedBook.getPrice());
            return Optional.of(bookToUpdate);
        }
        return Optional.empty();
    }

    public boolean deleteBook(Integer id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    // Filter functionalities
    public List<Book> getBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> getBooksCheaperThan(Double price) {
        return books.stream()
                .filter(book -> book.getPrice() < price)
                .collect(Collectors.toList());
    }

    // Bonus Tasks
    public long getTotalBooks() {
        return books.size();
    }

    public Optional<Book> getMostExpensiveBook() {
        return books.stream()
                .max((b1, b2) -> Double.compare(b1.getPrice(), b2.getPrice()));
    }
}