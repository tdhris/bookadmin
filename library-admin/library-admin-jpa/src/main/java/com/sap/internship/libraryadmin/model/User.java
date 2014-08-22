package com.sap.internship.libraryadmin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@Table(name = "LibraryUser")
@JsonSerialize
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String facultyNumber;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<BookLoan> bookLoans;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFacultyNumber() {
        return facultyNumber;
    }

    public void setFacultyNumber(String facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    @JsonIgnore
    public Collection<BookLoan> getBookLoans() {
        return bookLoans;
    }

    public void setBookLoans(Collection<BookLoan> bookLoans) {
        this.bookLoans = bookLoans;
    }

    @JsonIgnore
    public Collection<BookLoan> getActiveBookLoans() {
        Collection<BookLoan> activeLoans = new ArrayList<>();
        for (BookLoan loan : this.getBookLoans()) {
            if (loan.isActive()) {
                activeLoans.add(loan);
            }
        }
        return activeLoans;
    }

    @JsonIgnore
    public Collection<BookLoan> getOldBookLoans() {
        Collection<BookLoan> oldLoans = new ArrayList<>();
        for (BookLoan loan : this.getBookLoans()) {
            if (!loan.isActive()) {
                oldLoans.add(loan);
            }
        }
        return oldLoans;
    }

    public void addLoan(BookLoan loan) {
        this.getBookLoans().add(loan);
    }

    public boolean canTakeBook(Book book) {
        for (BookLoan bookLoan : this.getActiveBookLoans()) {
            if (book.equals(bookLoan.getBook())) {
                return false;
            }
        }

        return true;
    }

    public boolean hasTakenBook(Book book) {
        for (BookLoan bookLoan : this.getBookLoans()) {
            if (book.equals(bookLoan.getBook())) {
                return true;
            }
        }

        return false;
    }

    public BookLoan getActiveLoan(Book book) {
        for (BookLoan bookLoan : this.getActiveBookLoans()) {
            if (book.equals(bookLoan.getBook())) {
                return bookLoan;
            }
        }

        return null;
    }
}
