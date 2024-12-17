package com.library.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave primaria para Loan

    @ManyToOne
    @JoinColumn(name = "book_isbn") // Nombre de la columna que referencia la tabla Book
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id") // Nombre de la columna que referencia la tabla User
    private User user;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private boolean returned;

    public Loan(Book loanedBook1, User user, LocalDate loanDate, LocalDate dueDate, boolean b) {
    }

    public Loan() {

    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isOverdue() {
        return !returned && LocalDate.now().isAfter(dueDate);
    }

    public Long getId() {
        return id;
    }

}
