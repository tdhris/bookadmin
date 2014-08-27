package com.sap.internship.libraryadmin.model;

public class LoggedUser {

    private String name;

    public LoggedUser(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
