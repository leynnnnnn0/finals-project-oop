package model;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class Resident extends Database<Resident> {
    private int id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private Date date_of_birth;
    private String sex;
    private String contact_number;
    private String email;
    private String nationality;
    private String complete_address;
    private String password;

    public Resident auth() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/barangay_system",
                    "root",
                    ""
            );
            String query = "SELECT * FROM residents WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, getEmail());
            preparedStatement.setString(2, getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.id = resultSet.getInt("id");
                this.first_name = resultSet.getString("first_name");
                this.middle_name = resultSet.getString("middle_name");
                this.last_name = resultSet.getString("last_name");
                this.contact_number = resultSet.getString("contact_number");
                this.email = resultSet.getString("email");
                this.password = resultSet.getString("password");
                this.complete_address = resultSet.getString("complete_address");
                this.nationality = resultSet.getString("nationality");
                this.sex = resultSet.getString("sex");
                this.date_of_birth = Date.valueOf(resultSet.getString("date_of_birth"));
            }
            return this;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return this;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }


    public String getLast_name() {
        return last_name;
    }

    public String getSex() {
        return sex;
    }


    public String getContact_number() {
        return contact_number;
    }

    public String getEmail() {
        return email;
    }


    public String getNationality() {
        return nationality;
    }


    public String getComplete_address() {
        return complete_address;
    }

    public String getFullName()
    {
        return this.first_name + " " + this.last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Resident(String first_name, String middle_name, String last_name, Date date_of_birth, String sex, String contact_number, String email, String nationality, String complete_address, String password) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.sex = sex;
        this.contact_number = contact_number;
        this.email = email;
        this.nationality = nationality;
        this.complete_address = complete_address;
        this.password = password;
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
