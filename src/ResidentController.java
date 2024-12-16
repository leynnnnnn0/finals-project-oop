import helpers.ConfirmationDialogService;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Blotter;
import model.Resident;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ResidentController implements Initializable, ConfirmationDialogService {
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
    public AnchorPane residentViewPane;
    public Label infolistFullName;
    public Label infolistDateOfBirth;
    public Label infolistSex;
    public Label infolistContactNumber;
    public Label infolistEmail;
    public Label infolistNationality;
    public Label infolistCompleteAddress;
    public AnchorPane residentEditPane;
    public TextArea editCompleteAddress;
    public MFXTextField editFirstName;
    public MFXTextField editMiddleName;
    public MFXTextField editLastName;
    public MFXDatePicker editDateOfBirth;
    public MFXTextField editContactNumber;
    public MFXComboBox<String> editSex;
    public MFXTextField editEmail;
    public MFXFilterComboBox<String> editNationality;
    public Label editFirstNameError;
    public Label editMiddleNameError;
    public Label editLastNameError;
    public Label editDateOfBirthError;
    public Label editContactNumberError;
    public Label editSexError;
    public Label editEmailError;
    public Label editNationalityError;
    public Label editCompleteAddressError;


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
        editNationality.setItems(nationalityOptions);
        editSex.setItems(genderOptions);

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

        showConfirmationDialog(
                "Confirm Create",
                "Are you sure you want to create a new resident?",
                () -> {
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
        );
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

        table.getSelectionModel().selectionProperty().addListener((observable, oldValue, newValue) -> {
            Resident resident = table.getSelectionModel().getSelectedValues().getFirst();
            infolistFullName.setText(resident.getFullName());
            infolistDateOfBirth.setText(resident.getDate_of_birth().toString());
            infolistSex.setText(resident.getSex());
            infolistContactNumber.setText(resident.getContact_number());
            infolistEmail.setText(resident.getEmail());
            infolistNationality.setText(resident.getNationality());
            infolistCompleteAddress.setText(resident.getComplete_address());

            residentViewPane.setVisible(true);
            residentIndexPane.setVisible(false);

        });

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
            setError(firstNameError, "First name must be 2-50 characters, using only letters");
        }

        if (!middleName.getText().isEmpty()) {
            if (!middleName.getText().matches("^[a-zA-Z\\s]{1,50}$")) {
                setError(middleNameError, "Middle name must be 1-50 characters, using only letters");
            }
        }

        if (lastName.getText().isEmpty()) {
            setError(lastNameError, "Last name is required");
        } else if (!lastName.getText().matches("^[a-zA-Z\\s]{2,50}$")) {
            setError(lastNameError, "Last name must be 2-50 characters, using only letters");
        }

        if (dateOfBirth.getValue() == null) {
            setError(dateOfBirthError, "Date of birth is required");
        } else {
            LocalDate selectedDate = dateOfBirth.getValue();
            LocalDate today = LocalDate.now();
            LocalDate minValidDate = today.minusYears(120);
            LocalDate maxValidDate = today.minusYears(0);

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
        residentEditPane.setVisible(false);
        residentViewPane.setVisible(false);
    }

    public void setError(Label label, String message){
        errorsCounts++;
        label.setText(message);
    }

    public void updateResidentDetails(ActionEvent actionEvent) {
        if (!validateEditInputs()) {
            return;
        }

        showConfirmationDialog(
                "Confirm Update",
                "Are you sure you want to update this resident's details?",
                () -> {
                    Resident updatedResident = new Resident(
                            editFirstName.getText(),
                            editMiddleName.getText(),
                            editLastName.getText(),
                            Date.valueOf(editDateOfBirth.getValue()),
                            editSex.getSelectedItem(),
                            editContactNumber.getText(),
                            editEmail.getText(),
                            editNationality.getSelectedItem(),
                            editCompleteAddress.getText()
                    );

                    updatedResident.update(resident.getId() + "");

                    setTableData();

                    residentIndexPane.setVisible(true);
                    residentEditPane.setVisible(false);
                }
        );
    }

    private boolean validateEditInputs() {
        editFirstNameError.setText("");
        editMiddleNameError.setText("");
        editLastNameError.setText("");
        editContactNumberError.setText("");
        editDateOfBirthError.setText("");
        editSexError.setText("");
        editEmailError.setText("");
        editNationalityError.setText("");
        editCompleteAddressError.setText("");

        boolean isValid = true;

        if (editFirstName.getText().isEmpty()) {
            editFirstNameError.setText("First name is required");
            isValid = false;
        } else if (!editFirstName.getText().matches("^[a-zA-Z\\s]{2,50}$")) {
            editFirstNameError.setText("First name must be 2-50 characters, using only letters");
            isValid = false;
        }

        if (!editMiddleName.getText().isEmpty()) {
            if (!editMiddleName.getText().matches("^[a-zA-Z\\s]{1,50}$")) {
                editMiddleNameError.setText("Middle name must be 1-50 characters, using only letters");
                isValid = false;
            }
        }

        if (editLastName.getText().isEmpty()) {
            editLastNameError.setText("Last name is required");
            isValid = false;
        } else if (!editLastName.getText().matches("^[a-zA-Z\\s]{2,50}$")) {
            editLastNameError.setText("Last name must be 2-50 characters, using only letters");
            isValid = false;
        }

        if (editDateOfBirth.getValue() == null) {
            editDateOfBirthError.setText("Date of birth is required");
            isValid = false;
        } else {
            LocalDate selectedDate = editDateOfBirth.getValue();
            LocalDate today = LocalDate.now();
            LocalDate minValidDate = today.minusYears(120);
            LocalDate maxValidDate = today.minusYears(0);

            if (selectedDate.isAfter(today)) {
                editDateOfBirthError.setText("Date of birth cannot be in the future");
                isValid = false;
            } else if (selectedDate.isBefore(minValidDate)) {
                editDateOfBirthError.setText("Date of birth seems unrealistic (max 120 years old)");
                isValid = false;
            } else if (selectedDate.isAfter(maxValidDate)) {
                editDateOfBirthError.setText("You must be at least 18 years old");
                isValid = false;
            }
        }

        if (editSex.getSelectedItem() == null) {
            editSexError.setText("Gender is required");
            isValid = false;
        }

        if (editContactNumber.getText().isEmpty()) {
            editContactNumberError.setText("Contact number is required");
            isValid = false;
        } else if (!editContactNumber.getText().matches("^(09|\\+639)\\d{9}$")) {
            editContactNumberError.setText("Invalid phone number. Must start with 09 or +639 and be 10-12 digits");
            isValid = false;
        }

        if (editEmail.getText().isEmpty()) {
            editEmailError.setText("Email is required");
            isValid = false;
        } else if (!editEmail.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            editEmailError.setText("Invalid email format");
            isValid = false;
        }

        if (editNationality.getSelectedItem() == null) {
            editNationalityError.setText("Nationality is required");
            isValid = false;
        }

        if (editCompleteAddress.getText().isEmpty()) {
            editCompleteAddressError.setText("Complete address is required");
            isValid = false;
        } else if (editCompleteAddress.getText().length() < 10 || editCompleteAddress.getText().length() > 200) {
            editCompleteAddressError.setText("Address must be between 10 and 200 characters");
            isValid = false;
        }

        return isValid;
    }

    public void editResidentDetails(ActionEvent actionEvent) {
        Resident selectedResident = table.getSelectionModel().getSelectedValues().getFirst();
        resident = selectedResident;


        editFirstName.setText(selectedResident.getFirst_name());
        editMiddleName.setText(selectedResident.getMiddle_name());
        editLastName.setText(selectedResident.getLast_name());
        editDateOfBirth.setValue(Date.valueOf(selectedResident.getDate_of_birth().toString()).toLocalDate());
        editSex.selectItem(selectedResident.getSex());
        editContactNumber.setText(selectedResident.getContact_number());
        editEmail.setText(selectedResident.getEmail());
        editNationality.selectItem(selectedResident.getNationality());
        editCompleteAddress.setText(selectedResident.getComplete_address());

        residentIndexPane.setVisible(false);
        residentViewPane.setVisible(false);
        residentCreatePane.setVisible(false);
        residentEditPane.setVisible(true);
    }

    public void deleteResident(ActionEvent actionEvent) {
        showConfirmationDialog(
                "Confirm Delete",
                "Are you sure you want to delete this resident? This action cannot be undone.",
                () -> {
                    resident.delete(resident.getId());
                    setTableData();

                    residentIndexPane.setVisible(true);
                    residentViewPane.setVisible(false);
                    residentCreatePane.setVisible(false);
                    residentEditPane.setVisible(false);
                }
        );
    }
}
