package com.sap.internship.libraryadmin.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    public Book() {
    }

    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String author;
    private String description;
    private String copies;
    private String availableCopies;

    @ManyToMany(mappedBy = "books")
    private List<User> borrowers;

    @ManyToMany(mappedBy = "booksReturned")
    private List<User> borrowersHistory;

    public List<User> getBorrowers() {
        return borrowers;
    }

    public int takenCount() {
        return this.borrowers.size();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String param) {
        this.title = param;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String param) {
        this.author = param;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String param) {
        this.description = param;
    }

    public String getCopies() {
        return copies;
    }

    public void setCopies(String param) {
        this.copies = param;
    }

    public String getAvailableCopies() {
        return availableCopies;
    }

    public void takeCopy() {
        int current = Integer.parseInt(availableCopies);
        current--;
        this.availableCopies = Integer.toString(current);
    }

    public boolean hasAvailableCopies() {
        int allCopies = Integer.parseInt(copies);
        int current = Integer.parseInt(availableCopies);
        return ((allCopies - current) > 0);
    }

    public void setAvailableCopies(String availableCopies) {
        this.availableCopies = availableCopies;
    }

    public void setBorrowers(List<User> borrowers) {
        this.borrowers = borrowers;
    }

    public void setBorrowersHistory(List<User> borrowersHistory) {
        this.borrowersHistory = borrowersHistory;
    }

    public void borrowBook(User borrower) {
        this.takeCopy();
        this.borrowers.add(borrower);
    }

    public void removeBorrower(User borrower) {
        this.borrowers.remove(borrower);
        this.borrowersHistory.add(borrower);
    }

    public List<User> getBorrowersHistory() {
        return borrowersHistory;
    }

}