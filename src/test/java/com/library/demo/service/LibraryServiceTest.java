package com.library.demo.service;

import com.library.demo.exception.LibraryException;
import com.library.demo.model.Book;
import com.library.demo.model.Loan;
import com.library.demo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryServiceTest {

    private LibraryService service;

    @BeforeEach
    void setUp() {
        service = new LibraryService();

        // Crear libros de prueba
        Book book1 = new Book();
        book1.setIsbn("ISBN001");
        book1.setTitle("Java Programming");
        book1.setAuthor("Author A");
        book1.setAvailable(true);

        Book book2 = new Book();
        book2.setIsbn("ISBN002");
        book2.setTitle("Advanced Java");
        book2.setAuthor("Author B");
        book2.setAvailable(true);

        Book book3 = new Book();
        book3.setIsbn("ISBN003");
        book3.setTitle("Python Basics");
        book3.setAuthor("Author C");
        book3.setAvailable(true);

        Book book4 = new Book();
        book4.setIsbn("ISBN004");
        book4.setTitle("Clean Code");
        book4.setAuthor("Robert C. Martin");
        book4.setAvailable(true);

        // Crear usuarios de prueba
        User user1 = new User();
        user1.setId("USER001");
        user1.setName("Juan Pérez");
        user1.setEmail("juan@example.com");
        user1.setActiveLoans(new ArrayList<>());

        User user2 = new User();
        user2.setId("USER002");
        user2.setName("María López");
        user2.setEmail("maria@example.com");

        // Crear 3 préstamos para user2 (para probar límite)
        Book loanedBook1 = new Book();
        loanedBook1.setIsbn("ISBN010");
        loanedBook1.setTitle("C++ Guide");
        loanedBook1.setAuthor("Author D");
        loanedBook1.setAvailable(false);

        Book loanedBook2 = new Book();
        loanedBook2.setIsbn("ISBN011");
        loanedBook2.setTitle("JavaScript Essentials");
        loanedBook2.setAuthor("Author E");
        loanedBook2.setAvailable(false);

        Book loanedBook3 = new Book();
        loanedBook3.setIsbn("ISBN012");
        loanedBook3.setTitle("Kotlin In-Depth");
        loanedBook3.setAuthor("Author F");
        loanedBook3.setAvailable(false);

        Loan l1 = new Loan();
        l1.setBook(loanedBook1);
        l1.setUser(user2);
        l1.setLoanDate(LocalDate.now().minusDays(2));
        l1.setDueDate(LocalDate.now().plusDays(12));
        l1.setReturned(false);

        Loan l2 = new Loan();
        l2.setBook(loanedBook2);
        l2.setUser(user2);
        l2.setLoanDate(LocalDate.now().minusDays(5));
        l2.setDueDate(LocalDate.now().plusDays(9));
        l2.setReturned(false);

        Loan l3 = new Loan();
        l3.setBook(loanedBook3);
        l3.setUser(user2);
        l3.setLoanDate(LocalDate.now().minusDays(1));
        l3.setDueDate(LocalDate.now().plusDays(13));
        l3.setReturned(false);

        user2.setActiveLoans(new ArrayList<>(Arrays.asList(l1, l2, l3)));

        // Agregar datos al servicio
        service.getBooks().add(book1);
        service.getBooks().add(book2);
        service.getBooks().add(book3);
        service.getBooks().add(book4);

        service.getUsers().add(user1);
        service.getUsers().add(user2);

        service.getLoans().add(l1);
        service.getLoans().add(l2);
        service.getLoans().add(l3);
    }

    @Test
    void shouldFindBooksByTitle() {
        // Buscando "java" (ignorando mayúsculas)
        var foundBooks = service.findBooksByTitle("java");

        // Debería encontrar "Java Programming" y "Advanced Java"
        assertEquals(2, foundBooks.size());
        assertTrue(foundBooks.stream().anyMatch(b -> b.getTitle().equals("Java Programming")));
        assertTrue(foundBooks.stream().anyMatch(b -> b.getTitle().equals("Advanced Java")));
    }

    @Test
    void shouldFindBooksByAuthor() {
        // Buscando por autor "Author A"
        var foundBooks = service.findBooksByAuthor("Author A");
        // Debe encontrar solo el libro con author A
        assertEquals(1, foundBooks.size());
        assertEquals("Java Programming", foundBooks.get(0).getTitle());

        // Buscando un autor inexistente
        var noBooks = service.findBooksByAuthor("Non Existing");
        assertTrue(noBooks.isEmpty());
    }

    @Test
    void shouldLoanAvailableBook() throws LibraryException {
        Loan loan = service.loanBook("ISBN001", "USER001");
        assertNotNull(loan);
        assertEquals("ISBN001", loan.getBook().getIsbn());
        assertEquals("USER001", loan.getUser().getId());
        assertFalse(loan.getBook().isAvailable());
        assertEquals(1, loan.getUser().getActiveLoans().size());
    }

    @Test
    void shouldNotLoanUnavailableBook() {
        // Marcar ISBN002 como no disponible
        service.getBooks().stream()
                .filter(b -> b.getIsbn().equals("ISBN002"))
                .findFirst()
                .ifPresent(b -> b.setAvailable(false));

        // Intentar prestarlo
        assertThrows(LibraryException.class, () -> service.loanBook("ISBN002", "USER001"));
    }

    @Test
    void shouldNotExceedMaxLoans() {
        // El usuario USER002 ya tiene 3 préstamos
        assertThrows(LibraryException.class, () -> service.loanBook("ISBN001", "USER002"));
    }


}
