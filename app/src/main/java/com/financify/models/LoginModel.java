package com.financify.models;

public class LoginModel {
    private Integer id;
    private String username;
    private String email;
    private String password;

    public LoginModel(Integer id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {return id;}
    public String getUsername() {return username;};
    public String getEmail() {return email;}
    public String getPassword() {return password;}
}
