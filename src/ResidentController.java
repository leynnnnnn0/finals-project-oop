import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Resident;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ResidentController implements Initializable {
    public Resident resident;
    public AnchorPane residentIndexPane;
    public AnchorPane residentCreatePane;
    public MFXComboBox<String> genderComboBox;
    public MFXFilterComboBox<String> nationalityComboBox;

    public TextField firstName;
    public MFXTableView<Resident> table;
    public TextField middleName;
    public TextField email;
    public MFXDatePicker dateOfBirth;
    public TextField contactNumber;

    public TextArea completeAddress;
    public TextField lastName;

    public Label firstNameError;
    public Label middleNameError;
    public Label lastNameError;
    public Label dateOfBirthError;
    public Label sexError;
    public Label contactNumberError;
    public Label emailError;
    public Label nationalityError;
    public Label completeAddressError;

    public Map<String, String> errors;
    public int errorsCounts = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errors = new HashMap<>();
        ObservableList<String> genderOptions = FXCollections.observableArrayList();
        genderOptions.addAll(
                "Male",
                "Female"
        );
        ObservableList<String> nationalityOptions = FXCollections.observableArrayList();
        nationalityOptions.addAll(
                "Filipino",
                "American"
        );
        nationalityComboBox.setItems(nationalityOptions);
        genderComboBox.setItems(genderOptions);

        setupTable();

        setTableData();


        table.autosizeColumnsOnInitialization();
    }

    private void setTableData()
    {
        Resident residentModel = new Resident();
        ObservableList<Resident> residentData = residentModel.getAllRecords(Resident.class);
        table.setItems(residentData);
    }

    public void openCreateNewResidentPage(ActionEvent actionEvent) throws IOException {
        residentIndexPane.setVisible(false);
        residentCreatePane.setVisible(true);
    }

    public void createNewResident(ActionEvent actionEvent) {
        validateInputs();
        if(errorsCounts > 0){
            return;
        }
        Resident newResident = new Resident(
                firstName.getText(),
                middleName.getText(),
                lastName.getText(),
                Date.valueOf(dateOfBirth.getValue()),
                genderComboBox.getSelectedItem(),
                contactNumber.getText(),
                email.getText(),
                nationalityComboBox.getSelectedItem(),
                completeAddress.getText()
        );

        newResident.create();
        clearInputFields();
        setTableData();
        residentIndexPane.setVisible(true);
        residentCreatePane.setVisible(false);
    }

    private void setupTable() {
        MFXTableColumn<Resident> fullNameColumn = new MFXTableColumn<>("Full Name");
        MFXTableColumn<Resident> phoneNumberColumn = new MFXTableColumn<>("Contact Number");
        MFXTableColumn<Resident> emailColumn = new MFXTableColumn<>("Email");
        MFXTableColumn<Resident> sexColumn = new MFXTableColumn<>("Sex");
        MFXTableColumn<Resident> dobColumn = new MFXTableColumn<>("Date of Birth");
        MFXTableColumn<Resident> nationalityColumn = new MFXTableColumn<>("Nationality");


        fullNameColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(Resident::getFullName));
        phoneNumberColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(Resident::getContact_number));
        emailColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(Resident::getEmail));
        nationalityColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(Resident::getNationality));
        sexColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(Resident::getSex));
        dobColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(Resident::getDate_of_birth));


        table.getTableColumns().addAll(fullNameColumn, phoneNumberColumn, emailColumn, sexColumn, dobColumn, nationalityColumn);



    }

    private void clearInputFields() {
        firstName.clear();
        middleName.clear();
        lastName.clear();
        dateOfBirth.setValue(null);
        email.clear();
        genderComboBox.setValue(null);
        contactNumber.clear();
        nationalityComboBox.setValue(null);
        completeAddress.clear();
    }

    private void clearError() {
        errorsCounts = 0;
        firstNameError.setText("");
        middleNameError.setText("");
        lastNameError.setText("");
        contactNumberError.setText("");
        dateOfBirthError.setText("");
        sexError.setText("");
        emailError.setText("");
        nationalityError.setText("");
        completeAddressError.setText("");

    }

    private void validateInputs() {
        clearError();

        if (firstName.getText().isEmpty()) {
            setError(firstNameError, "First name is required");
        } else if (!firstName.getText().matches("^[a-zA-Z\\s]{2,50}$")) {
            setError(firstNameError, "First name must be 2-50 characters, using only letters and spaces");
        }

        if (!middleName.getText().isEmpty()) {
            if (!middleName.getText().matches("^[a-zA-Z\\s]{1,50}$")) {
                setError(middleNameError, "Middle name must be 1-50 characters, using only letters and spaces");
            }
        }

        if (lastName.getText().isEmpty()) {
            setError(lastNameError, "Last name is required");
        } else if (!lastName.getText().matches("^[a-zA-Z\\s]{2,50}$")) {
            setError(lastNameError, "Last name must be 2-50 characters, using only letters and spaces");
        }

        if (dateOfBirth.getValue() == null) {
            setError(dateOfBirthError, "Date of birth is required");
        } else {
            LocalDate selectedDate = dateOfBirth.getValue();
            LocalDate today = LocalDate.now();
            LocalDate minValidDate = today.minusYears(120);
            LocalDate maxValidDate = today.minusYears(18); // Assuming minimum age is 18

            if (selectedDate.isAfter(today)) {
                setError(dateOfBirthError, "Date of birth cannot be in the future");
            } else if (selectedDate.isBefore(minValidDate)) {
                setError(dateOfBirthError, "Date of birth seems unrealistic (max 120 years old)");
            } else if (selectedDate.isAfter(maxValidDate)) {
                setError(dateOfBirthError, "You must be at least 18 years old");
            }
        }

        if (genderComboBox.getSelectedItem() == null) {
            setError(sexError, "Gender is required");
        }

        if (contactNumber.getText().isEmpty()) {
            setError(contactNumberError, "Contact number is required");
        } else if (!contactNumber.getText().matches("^(09|\\+639)\\d{9}$")) {
            setError(contactNumberError, "Invalid phone number. Must start with 09 or +639 and be 10-12 digits");
        }

        if (email.getText().isEmpty()) {
            setError(emailError, "Email is required");
        } else if (!email.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            setError(emailError, "Invalid email format");
        }

        if (nationalityComboBox.getSelectedItem() == null) {
            setError(nationalityError, "Nationality is required");
        }

        if (completeAddress.getText().isEmpty()) {
            setError(completeAddressError, "Complete address is required");
        } else if (completeAddress.getText().length() < 10 || completeAddress.getText().length() > 200) {
            setError(completeAddressError, "Address must be between 10 and 200 characters");
        }
    }

    public void backToIndex(ActionEvent actionEvent) {
        residentIndexPane.setVisible(true);
        residentCreatePane.setVisible(false);
    }

    public void setError(Label label, String message){
        errorsCounts++;
        label.setText(message);
    }

}
