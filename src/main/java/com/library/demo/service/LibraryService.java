package com.library.demo.service;

import com.library.demo.exception.LibraryException;
import com.library.demo.model.Book;
import com.library.demo.model.Loan;
import com.library.demo.model.User;
import com.library.demo.repository.BookRepository;
import com.library.demo.repository.LoanRepository;
import com.library.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository, LoanRepository loanRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }


    public List<Book> findBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }


    public List<Book> findBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public Loan loanBook(String isbn, String userId) throws LibraryException {
        // 1. Validar que el libro existe y está disponible
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new LibraryException("El libro con ISBN " + isbn + " no existe."));

        if (!book.isAvailable()) {
            throw new LibraryException("El libro no está disponible para préstamo.");
        }

        // 2. Validar que el usuario existe y puede pedir prestados más libros
        User user = userRepository.findById(userId)
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

        // 5. Guardar los cambios en la base de datos
        bookRepository.save(book);
        loanRepository.save(newLoan);

        // 6. Opcional: Actualizar el estado del usuario si es necesario
        userRepository.save(user);

        return newLoan;
    }


}


