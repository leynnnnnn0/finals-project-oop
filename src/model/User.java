package model;

import database.Database;

public class User extends Database<User> {
    private String first_name;
    private String middle_name;
    private String last_name;
    private String phone_number;
    private String email;
    private boolean is_admin;

    public User(String first_name, String middle_name, String last_name, String phone_number, String email, boolean is_admin) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email = email;
        this.is_admin = is_admin;
    }

    @Override
    protected String getDatabaseName() {
        return "users";
    }

    public User(){}

    public String getFullName()
    {
        return this.first_name + " " + this.last_name;
    }

    public boolean isAdmin() {
        return is_admin;
    }

    public String getLastName() {
        return last_name;
    }

    public String getMiddleName() {
        return middle_name;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phone_number;
    }
}
