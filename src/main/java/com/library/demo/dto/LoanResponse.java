package com.library.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanResponse {

    private Long id;
    private String isbn;
    private String userId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private boolean returned;

    /**
     * Constructor sin argumentos.
     * Útil para frameworks que requieren un constructor por defecto.
     */
    public LoanResponse() {
    }

    /**
     * Constructor con todos los campos.
     *
     * @param id        El ID del préstamo.
     * @param isbn      El ISBN del libro prestado.
     * @param userId    El ID del usuario que realizó el préstamo.
     * @param loanDate  La fecha en que se realizó el préstamo.
     * @param dueDate   La fecha de devolución del libro.
     * @param returned  Indica si el libro ha sido devuelto.
     */
    public LoanResponse(Long id, String isbn, String userId, LocalDate loanDate, LocalDate dueDate, boolean returned) {
        this.id = id;
        this.isbn = isbn;
        this.userId = userId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returned = returned;
    }

    // Getters y Setters

    /**
     * Obtiene el ID del préstamo.
     *
     * @return El ID del préstamo.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del préstamo.
     *
     * @param id El ID del préstamo.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el ISBN del libro prestado.
     *
     * @return El ISBN del libro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Establece el ISBN del libro prestado.
     *
     * @param isbn El ISBN del libro.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtiene el ID del usuario que realizó el préstamo.
     *
     * @return El ID del usuario.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Establece el ID del usuario que realizó el préstamo.
     *
     * @param userId El ID del usuario.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Obtiene la fecha en que se realizó el préstamo.
     *
     * @return La fecha del préstamo.
     */
    public LocalDate getLoanDate() {
        return loanDate;
    }

    /**
     * Establece la fecha en que se realizó el préstamo.
     *
     * @param loanDate La fecha del préstamo.
     */
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * Obtiene la fecha de devolución del libro.
     *
     * @return La fecha de devolución.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Establece la fecha de devolución del libro.
     *
     * @param dueDate La fecha de devolución.
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Indica si el libro ha sido devuelto.
     *
     * @return {@code true} si el libro ha sido devuelto; {@code false} en caso contrario.
     */
    public boolean isReturned() {
        return returned;
    }

    /**
     * Establece si el libro ha sido devuelto.
     *
     * @param returned {@code true} si el libro ha sido devuelto; {@code false} en caso contrario.
     */
    public void setReturned(boolean returned) {
        this.returned = returned;
    }


}
