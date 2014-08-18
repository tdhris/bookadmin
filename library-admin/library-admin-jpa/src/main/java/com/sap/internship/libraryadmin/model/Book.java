package com.sap.internship.libraryadmin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

}