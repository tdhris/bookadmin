package com.sap.internship.libraryadmin.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@NamedQuery(name = "AllUsers", query = "SELECT u FROM User u")
@JsonSerialize
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String facultyNumber;

    @ManyToMany
    @JoinTable(name = "B_U", joinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
    private List<Book> booksTaken;

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

    // public List<Book> getBooksTaken() {
    // return booksTaken;
    // }
}
