package model;

import database.Database;

import java.sql.*;

public class User extends Database<User> {
    private String first_name;
    private String middle_name;
    private String last_name;
    private String phone_number;
    private String email;
    private String passcode;
    private boolean is_admin;

    public User(String first_name, String middle_name, String last_name, String phone_number, String email, boolean is_admin) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email = email;
        this.is_admin = is_admin;
        this.passcode = getLastName() + getPhoneNumber();
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole(){
        return is_admin ? "Admin" : "Sub Admin";
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public boolean auth()  {
        try{
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/barangay_system",
                    "root",
                    ""
            );
            String query = "SELECT * FROM users WHERE email = ? AND passcode = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, getEmail());
            preparedStatement.setString(2, getPasscode());
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
