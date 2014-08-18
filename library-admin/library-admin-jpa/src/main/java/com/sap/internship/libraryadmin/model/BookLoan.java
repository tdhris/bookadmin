package com.sap.internship.libraryadmin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookLoan {
    @Id
    @GeneratedValue
    private long id;
}
