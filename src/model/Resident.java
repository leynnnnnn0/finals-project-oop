package model;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Resident extends Database<Resident> {
    private String first_name;
    private String middle_name;
    private String last_name;
    private Date date_of_birth;
    private String sex;
    private String contact_number;
    private String email;
    private String nationality;
    private String complete_address;

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getComplete_address() {
        return complete_address;
    }

    public String getFullName()
    {
        return this.first_name + " " + this.last_name;
    }

    public void setComplete_address(String complete_address) {
        this.complete_address = complete_address;
    }

    public Resident(String first_name, String middle_name, String last_name, Date date_of_birth, String sex, String contact_number, String email, String nationality, String complete_address) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.sex = sex;
        this.contact_number = contact_number;
        this.email = email;
        this.nationality = nationality;
        this.complete_address = complete_address;
    }

    public Resident(){}



    @Override
    protected String getDatabaseName() {
        return "residents";
    }
}
