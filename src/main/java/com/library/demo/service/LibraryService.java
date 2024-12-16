package com.library.demo.service;

import com.library.demo.exception.LibraryException;
import com.library.demo.model.Book;
import com.library.demo.model.Loan;
import com.library.demo.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private List<Book> books = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    // TODO: Implementar este método
    public List<Book> findBooksByTitle(String title) {
        if (title==null || title.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String searchTitle = title.toLowerCase();

        return books.stream().filter(book -> book.getTitle() != null
                        && book.getTitle().toLowerCase().contains(searchTitle))
                .collect(Collectors.toList());
    }

    // TODO: Implementar este método
    public List<Book> findBooksByAuthor(String author) {
        if (author==null || author.trim().isEmpty()) {
            return Collections.emptyList();
        }
        String searchAuthor = author.toLowerCase();

        return books.stream()
                // Filtramos los libros cuyo título contenga la cadena dada, ignorando mayúsculas/minúsculas
                .filter(book -> book != null
                        && book.getAuthor().toLowerCase().contains(searchAuthor))
                .collect(Collectors.toList());
    }

    public Loan loanBook(String isbn, String userId) throws LibraryException {
        // 1. Validar que el libro existe y está disponible
        Book book = books.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new LibraryException("El libro con ISBN " + isbn + " no existe."));

        if (!book.isAvailable()) {
            throw new LibraryException("El libro no está disponible para préstamo.");
        }

        // 2. Validar que el usuario existe y puede pedir prestados más libros
        User user = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new LibraryException("El usuario con ID " + userId + " no existe."));

        if (!user.canBorrowBooks()) {
            throw new LibraryException("El usuario no puede pedir más préstamos (máximo 3 activos).");
        }

        // 3. Crear el préstamo con fecha de devolución a 14 días
        Loan newLoan = new Loan();
        newLoan.setBook(book);
        newLoan.setUser(user);
        newLoan.setLoanDate(LocalDate.now());
        newLoan.setDueDate(LocalDate.now().plusDays(14));
        newLoan.setReturned(false);

        // 4. Actualizar el estado del libro
        book.setAvailable(false);

        // Agregamos el préstamo a la lista global de préstamos y a la lista de préstamos activos del usuario
        loans.add(newLoan);
        user.getActiveLoans().add(newLoan);

        return newLoan;
    }

    }


