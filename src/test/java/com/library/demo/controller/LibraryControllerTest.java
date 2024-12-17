package com.library.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.demo.dto.LoanRequest;
import com.library.demo.dto.LoanResponse;
import com.library.demo.exception.LibraryException;
import com.library.demo.model.Book;
import com.library.demo.model.Loan;
import com.library.demo.model.User;
import com.library.demo.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LibraryService libraryService;

    @Autowired
    private ObjectMapper objectMapper;

    private LoanRequest validLoanRequest;
    private LoanResponse loanResponse;
    private Book book1;
    private Book book2;
    private User user;
    private Loan loan;

    @BeforeEach
    void setUp() {
        // Configuración de LoanRequest
        validLoanRequest = new LoanRequest();
        validLoanRequest.setIsbn("1234567890");
        validLoanRequest.setUserId("usr123");

        // Configuración de User
        user = new User();
        user.setId("usr123");
        user.setName("John Doe");

        // Configuración de Book
        Book book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("Spring in Action");
        book.setAuthor("Craig Walls");

        // Configuración de Loan
        loan = new Loan();
        loan.setId();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusWeeks(2));
        loan.setReturned(false);

        // Configuración de LoanResponse
        loanResponse = new LoanResponse(
                loan.getId(),
                loan.getBook().getIsbn(),
                loan.getUser().getId(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.isReturned()
        );

        // Configuración de Books para búsquedas
        book1 = new Book();
        book1.setIsbn("1111111111");
        book1.setTitle("Effective Java");
        book1.setAuthor("Joshua Bloch");

        book2 = new Book();
        book2.setIsbn("2222222222");
        book2.setTitle("Clean Code");
        book2.setAuthor("Robert C. Martin");
    }


    @Test
    @DisplayName("POST /library/loan - Fallo por LibraryException")
    void testLoanBook_LibraryException() throws Exception {
        // Mockear el servicio para lanzar una LibraryException
        String errorMessage = "Libro no disponible para préstamo";
        doThrow(new LibraryException(errorMessage))
                .when(libraryService).loanBook(anyString(), anyString());

        // Realizar la solicitud POST
        mockMvc.perform(post("/library/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoanRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }

    @Test
    @DisplayName("GET /library/books - Buscar por título")
    void testFindBooksByTitle() throws Exception {
        String titleQuery = "Effective Java";

        List<Book> books = Arrays.asList(book1);

        // Mockear el servicio para devolver la lista de libros
        given(libraryService.findBooksByTitle(titleQuery)).willReturn(books);

        // Realizar la solicitud GET
        mockMvc.perform(get("/library/books")
                        .param("title", titleQuery)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Verificar que el array tiene tamaño 1
                .andExpect(jsonPath("$", hasSize(1)))
                // Verificar los campos del primer libro
                .andExpect(jsonPath("$[0].isbn", is(book1.getIsbn())))
                .andExpect(jsonPath("$[0].title", is(book1.getTitle())))
                .andExpect(jsonPath("$[0].author", is(book1.getAuthor())));
    }

    @Test
    @DisplayName("GET /library/authors - Buscar por autor")
    void testFindBooksByAuthor() throws Exception {
        String authorQuery = "Robert C. Martin";

        List<Book> books = Arrays.asList(book2);

        // Mockear el servicio para devolver la lista de libros
        given(libraryService.findBooksByAuthor(authorQuery)).willReturn(books);

        // Realizar la solicitud GET
        mockMvc.perform(get("/library/authors")
                        .param("author", authorQuery)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Verificar que el array tiene tamaño 1
                .andExpect(jsonPath("$", hasSize(1)))
                // Verificar los campos del primer libro
                .andExpect(jsonPath("$[0].isbn", is(book2.getIsbn())))
                .andExpect(jsonPath("$[0].title", is(book2.getTitle())))
                .andExpect(jsonPath("$[0].author", is(book2.getAuthor())));
    }

    @Test
    @DisplayName("GET /library/books - Sin resultados")
    void testFindBooksByTitle_NoResults() throws Exception {
        String titleQuery = "Nonexistent Book";

        List<Book> books = Arrays.asList();

        // Mockear el servicio para devolver una lista vacía
        given(libraryService.findBooksByTitle(titleQuery)).willReturn(books);

        // Realizar la solicitud GET
        mockMvc.perform(get("/library/books")
                        .param("title", titleQuery)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Verificar que el array está vacío
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("GET /library/authors - Sin resultados")
    void testFindBooksByAuthor_NoResults() throws Exception {
        String authorQuery = "Unknown Author";

        List<Book> books = Arrays.asList();

        // Mockear el servicio para devolver una lista vacía
        given(libraryService.findBooksByAuthor(authorQuery)).willReturn(books);

        // Realizar la solicitud GET
        mockMvc.perform(get("/library/authors")
                        .param("author", authorQuery)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Verificar que el array está vacío
                .andExpect(jsonPath("$", hasSize(0)));
    }


}
