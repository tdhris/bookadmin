package com.sap.internship.libraryadmin.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@Table(name = "LibraryUser")
@JsonSerialize
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String facultyNumber;

    @ManyToMany
    @JoinTable(name = "US_BOOK_ACT", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") })
    private List<Book> books;

    @ManyToMany
    @JoinTable(name = "US_BOOK_RET", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") })
    private List<Book> booksReturned;

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

    public List<Book> getBooks() {
        return books;
    }

    public List<Book> getBooksReturned() {
        return booksReturned;
    }

    public void borrowBook(Book book) {
        this.books.add(book);
    }

    public void returnBook(Book book) {
        this.books.remove(book);
        this.booksReturned.add(book);
    }
}
