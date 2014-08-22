package com.sap.internship.libraryadmin.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    public Book() {
        String copies = this.getCopies();
        this.setAvailableCopies(copies);
        // ArrayList<User> borrowers = new ArrayList<>();
        // this.setBorrowers(borrowers);
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;
    private String title;
    private String author;
    private String description;
    private String copies;
    private String availableCopies;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<BookLoan> bookLoans;

    // @ManyToMany(cascade = CascadeType.ALL, mappedBy = "books", targetEntity = User.class)
    // @JoinTable(name = "US_BOOK_ACT", joinColumns = { @JoinColumn(name = "BOOK_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
    // @ManyToMany(mappedBy = "books")
    // private Collection<User> borrowers;

    // @ManyToMany(mappedBy = "booksReturned")
    // private List<User> borrowersHistory;

    // public Collection<User> getBorrowers() {
    // return borrowers;
    // }

    // public int takenCount() {
    // return this.borrowers.size();
    // }

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

    public int getCopiesInt() {
        return Integer.parseInt(copies);
    }

    public void setCopies(String param) {
        this.copies = param;
    }

    public String getAvailableCopies() {
        return availableCopies;
    }

    public int getAvailableCopiesInt() {
        return Integer.parseInt(availableCopies);
    }

    public void takeCopy() {
        int current = getAvailableCopiesInt();
        current--;
        this.setAvailableCopies(Integer.toString(current));
    }

    public boolean hasAvailableCopies() {
        return this.getAvailableCopiesInt() > 0;
    }

    public void setAvailableCopies(String availableCopies) {
        this.availableCopies = availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.setAvailableCopies(Integer.toString(availableCopies));
    }

    public void syncAvailableCopies() {
        int copiesTaken = this.takenCount();
        int exactCopiesAvailable = this.getCopiesInt() - copiesTaken;
        this.setAvailableCopies(exactCopiesAvailable);
    }

    public Collection<BookLoan> getBookLoans() {
        // return bookLoans;
        return null;
    }

    public void setBookLoans(Collection<BookLoan> bookLoans) {
        // this.bookLoans = bookLoans;

    }

    public int takenCount() {
        return this.getBookLoans().size();
        // return this.getBorrowers().size();
    }

    // public void setBorrowers(Collection<User> borrowers) {
    // this.borrowers = borrowers;
    // }

    // public void setBorrowersHistory(List<User> borrowersHistory) {
    // this.borrowersHistory = borrowersHistory;
    // s}

    // public void borrowBook(User borrower) {
    // this.takeCopy();
    // this.borrowers.add(borrower);
    // }

    // public void removeBorrower(User borrower) {
    // this.borrowers.remove(borrower);
    // this.borrowersHistory.add(borrower);
    // }
    //
    // public List<User> getBorrowersHistory() {
    // return borrowersHistory;
    // }

}