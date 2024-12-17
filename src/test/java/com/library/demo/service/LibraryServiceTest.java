package com.library.demo.service;

import com.library.demo.exception.LibraryException;
import com.library.demo.model.Book;
import com.library.demo.model.Loan;
import com.library.demo.model.User;
import com.library.demo.repository.BookRepository;
import com.library.demo.repository.LoanRepository;
import com.library.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibraryServiceTest {

    private LibraryService service;
    private BookRepository bookRepository;
    private LoanRepository loanRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        loanRepository = mock(LoanRepository.class);
        userRepository = mock(UserRepository.class);

        service = new LibraryService(bookRepository, loanRepository, userRepository);

        // Crear libros simulados
        Book book1 = new Book("ISBN001", "Java Programming", "Author A", true);
        Book book2 = new Book("ISBN002", "Advanced Java", "Author B", true);

        // Configurar comportamiento de los mocks
        when(bookRepository.findByTitleContainingIgnoreCase("java"))
                .thenReturn(Arrays.asList(book1, book2));
        when(bookRepository.findByAuthorContainingIgnoreCase("Author A"))
                .thenReturn(Collections.singletonList(book1));
        when(bookRepository.findByAuthorContainingIgnoreCase("Non Existing"))
                .thenReturn(Collections.emptyList());
        when(bookRepository.findById("ISBN001")).thenReturn(Optional.of(book1));
        when(bookRepository.findById("ISBN002")).thenReturn(Optional.of(book2));

        // Crear usuario simulado
        User user1 = new User("USER001", "Juan Pérez", "juan@example.com", new ArrayList<>());
        User user2 = new User("USER002", "María López", "maria@example.com", new ArrayList<>());

        // Configurar usuario con 3 préstamos activos
        Book loanedBook1 = new Book("ISBN010", "C++ Guide", "Author D", false);
        Book loanedBook2 = new Book("ISBN011", "JavaScript Essentials", "Author E", false);
        Book loanedBook3 = new Book("ISBN012", "Kotlin In-Depth", "Author F", false);

        Loan l1 = new Loan(loanedBook1, user2, LocalDate.now().minusDays(2), LocalDate.now().plusDays(12), false);
        Loan l2 = new Loan(loanedBook2, user2, LocalDate.now().minusDays(5), LocalDate.now().plusDays(9), false);
        Loan l3 = new Loan(loanedBook3, user2, LocalDate.now().minusDays(1), LocalDate.now().plusDays(13), false);

        user2.setActiveLoans(Arrays.asList(l1, l2, l3));

        when(userRepository.findById("USER001")).thenReturn(Optional.of(user1));
        when(userRepository.findById("USER002")).thenReturn(Optional.of(user2));
    }

    @Test
    void shouldFindBooksByTitle() {
        var foundBooks = service.findBooksByTitle("java");
        assertEquals(2, foundBooks.size());
        assertTrue(foundBooks.stream().anyMatch(b -> b.getTitle().equals("Java Programming")));
        assertTrue(foundBooks.stream().anyMatch(b -> b.getTitle().equals("Advanced Java")));
    }

    @Test
    void shouldFindBooksByAuthor() {
        var foundBooks = service.findBooksByAuthor("Author A");
        assertEquals(1, foundBooks.size());
        assertEquals("Java Programming", foundBooks.get(0).getTitle());

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

        verify(bookRepository, times(1)).save(Mockito.any(Book.class));
        verify(loanRepository, times(1)).save(Mockito.any(Loan.class));
    }

    @Test
    void shouldNotLoanUnavailableBook() {
        Book unavailableBook = new Book("ISBN002", "Advanced Java", "Author B", false);
        when(bookRepository.findById("ISBN002")).thenReturn(Optional.of(unavailableBook));

        assertThrows(LibraryException.class, () -> service.loanBook("ISBN002", "USER001"));
    }

    @Test
    void shouldNotExceedMaxLoans() {
        assertThrows(LibraryException.class, () -> service.loanBook("ISBN001", "USER002"));
    }
}
