package com.sap.internship.libraryadmin.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookLoan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Book book;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private User user;

    private String dateTaken;

    private String dateReturned;

    @Transient
    @JsonIgnore
    private static final String dateFormat = "dd.MM.yyyy HH:mm";

    public BookLoan() {
        this.setDateTaken(this.getNow());
        this.setDateReturned(null);
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("dateTaken")
    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    @JsonProperty("dateReturned")
    public String getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(String dateReturned) {
        this.dateReturned = dateReturned;
    }

    public void returnBook() {
        this.setDateReturned(this.getNow());
    }

    public boolean isActive() {
        return this.getDateReturned() == null;
    }

    private String getNow() {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }
}
