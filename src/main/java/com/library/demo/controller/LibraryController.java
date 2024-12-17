package com.library.demo.controller;

import com.library.demo.dto.LoanRequest;
import com.library.demo.dto.LoanResponse;
import com.library.demo.exception.LibraryException;
import com.library.demo.model.Book;
import com.library.demo.model.Loan;
import com.library.demo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    // Endpoint para prestar un libro
    // Parámetros enviados vía body request: isbn=1234&userId=usr123
    @PostMapping("/loan")
    public ResponseEntity<?> loanBook(@Validated @RequestBody LoanRequest loanRequest) {
        try {
            Loan newLoan = libraryService.loanBook(loanRequest.getIsbn(), loanRequest.getUserId());
            // Mapeo manual a LoanResponse
            LoanResponse response = new LoanResponse(
                    newLoan.getId(),
                    newLoan.getBook().getIsbn(),
                    newLoan.getUser().getId(),
                    newLoan.getLoanDate(),
                    newLoan.getDueDate(),
                    newLoan.isReturned()
            );
            return ResponseEntity.ok(response);
        } catch (LibraryException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // Endpoint para buscar libros por título (ignorando mayúsculas/minúsculas)
    // Ejemplo: /library/books?title=tituloDelLibro
    @GetMapping("/books")
    public ResponseEntity<List<Book>> findBooksByTitle(@RequestParam String title) {
        List<Book> foundBooks = libraryService.findBooksByTitle(title);
        return ResponseEntity.ok(foundBooks);
    }


    // Endpoint para buscar libros por autor (ignorando mayúsculas/minúsculas)
    // Ejemplo: /library/authors?author=nombreDelAuthor
    @GetMapping("/authors")
    public ResponseEntity<List<Book>> findBooksByAuthor(@RequestParam String author) {
        List<Book> foundAuthors = libraryService.findBooksByAuthor(author);
        return ResponseEntity.ok(foundAuthors);
    }



}
