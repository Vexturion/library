package com.library.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {

    private String id;
    private String name;
    private String email;
    private List<Loan> activeLoans;
    // TODO: Implementar constructor, getters y setters

    public User() {}

    public User(String id, String name, String email, List<Loan> activeLoans) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.activeLoans = activeLoans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Loan> getActiveLoans() {
        return activeLoans;
    }

    public void setActiveLoans(List<Loan> activeLoans) {
        this.activeLoans = activeLoans;
    }

    public boolean canBorrowBooks() {
        return activeLoans.size() < 3;
    }

}
