import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import model.Resident;

public class SignupController implements Initializable {
    @FXML private MFXTextField firstName;
    @FXML private MFXTextField middleName;
    @FXML private MFXTextField lastName;
    @FXML private MFXDatePicker dateOfBirth;
    @FXML private MFXComboBox<String> genderComboBox;
    @FXML private MFXTextField contactNumber;
    @FXML private MFXTextField email;
    @FXML private MFXPasswordField password;
    @FXML private MFXComboBox<String> nationalityComboBox;
    @FXML private MFXTextField completeAddress;

    @FXML private Label firstNameError;
    @FXML private Label lastNameError;
    @FXML private Label dateOfBirthError;
    @FXML private Label genderError;
    @FXML private Label contactNumberError;
    @FXML private Label emailError;
    @FXML private Label passwordError;
    @FXML private Label nationalityError;
    @FXML private Label completeAddressError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderComboBox.getItems().addAll("Male", "Female");
        nationalityComboBox.getItems().addAll("Filipino", "Others");

        contactNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                contactNumber.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Clear previous errors
        firstNameError.setText("");
        lastNameError.setText("");
        dateOfBirthError.setText("");
        genderError.setText("");
        contactNumberError.setText("");
        emailError.setText("");
        passwordError.setText("");
        nationalityError.setText("");
        completeAddressError.setText("");

        // Existing validations...
        if (firstName.getText().trim().isEmpty()) {
            firstNameError.setText("First name is required");
            isValid = false;
        }

        if (lastName.getText().trim().isEmpty()) {
            lastNameError.setText("Last name is required");
            isValid = false;
        }

        if (dateOfBirth.getValue() == null) {
            dateOfBirthError.setText("Date of birth is required");
            isValid = false;
        }

        if (genderComboBox.getSelectedItem() == null) {
            genderError.setText("Gender is required");
            isValid = false;
        }

        if (contactNumber.getText().trim().isEmpty()) {
            contactNumberError.setText("Contact number is required");
            isValid = false;
        } else if (contactNumber.getText().length() != 11) {
            contactNumberError.setText("Contact number must be 11 digits");
            isValid = false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (email.getText().trim().isEmpty()) {
            emailError.setText("Email is required");
            isValid = false;
        } else if (!email.getText().matches(emailRegex)) {
            emailError.setText("Invalid email format");
            isValid = false;
        }

        // Password validation
        if (password.getText().trim().isEmpty()) {
            passwordError.setText("Password is required");
            isValid = false;
        } else if (password.getText().length() < 8) {
            passwordError.setText("Password must be at least 8 characters");
            isValid = false;
        }

        if (nationalityComboBox.getSelectedItem() == null) {
            nationalityError.setText("Nationality is required");
            isValid = false;
        }

        if (completeAddress.getText().trim().isEmpty()) {
            completeAddressError.setText("Complete address is required");
            isValid = false;
        }

        return isValid;
    }

    @FXML
    private void signup(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }

        try {
            Resident newResident = new Resident(
                    firstName.getText(),
                    middleName.getText(),
                    lastName.getText(),
                    Date.valueOf(dateOfBirth.getValue()),
                    genderComboBox.getSelectedItem(),
                    contactNumber.getText(),
                    email.getText(),
                    nationalityComboBox.getSelectedItem(),
                    completeAddress.getText(),
                    password.getText()  // Now using the actual password instead of generating one
            );

            newResident.create();
            showSuccessNotification("Success", "Registration successful! You can now login.");
            backToLogin(event);

        } catch (Exception e) {
            showErrorNotification("Error", e.getMessage());
        }
    }

    @FXML
    private void backToLogin(ActionEvent event) {
        // Add your navigation logic here
    }

    private void showSuccessNotification(String title, String content) {
        // Implement your success notification
    }

    private void showErrorNotification(String title, String content) {
        // Implement your error notification
    }
}