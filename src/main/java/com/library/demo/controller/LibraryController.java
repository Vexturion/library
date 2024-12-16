package com.library.demo.controller;

import com.library.demo.exception.LibraryException;
import com.library.demo.model.Book;
import com.library.demo.model.Loan;
import com.library.demo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    // Endpoint para prestar un libro
    // Parámetros recibidos vía query params: /library/loan?isbn=1234&userId=usr123
    @PostMapping("/loan")
    public ResponseEntity<Loan> loanBook(@RequestParam String isbn, @RequestParam String userId) {
        try {
            Loan newLoan = libraryService.loanBook(isbn, userId);
            return ResponseEntity.ok(newLoan);
        } catch (LibraryException e) {
            // Si ocurre algún error (por ejemplo, el libro no existe, no está disponible o el usuario no puede tomar más préstamos)
            // retornamos un Bad Request con el detalle del error.
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint para buscar libros por título (ignorando mayúsculas/minúsculas)
    // Ejemplo: /library/books?title=java
    @GetMapping("/books")
    public ResponseEntity<List<Book>> findBooksByTitle(@RequestParam String title) {
        List<Book> foundBooks = libraryService.findBooksByTitle(title);
        return ResponseEntity.ok(foundBooks);
    }


    // Endpoint para buscar libros por título (ignorando mayúsculas/minúsculas)
    // Ejemplo: /library/books?title=java
    @GetMapping("/authors")
    public ResponseEntity<List<Book>> findBooksByAuthor(@RequestParam String author) {
        List<Book> foundBooks = libraryService.findBooksByAuthor(author);
        return ResponseEntity.ok(foundBooks);
    }



}
