package com.main.app.data.models;

import lombok.Data;

@Data
public class User {
    public String id;
    public String name;
    public String email;
    public  String password;
    public String role;
}
