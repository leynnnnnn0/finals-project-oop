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

    private void validateInputs()
    {
        clearError();
        if(firstName.getText().isEmpty())
            setError(firstNameError, "First name is required");

        if(!firstName.getText().isEmpty() && firstName.getText().matches("^[a-zA-Z]+$"))
            setError(firstNameError, "First name should only contain letters");

        if(lastName.getText().isEmpty())
            setError(lastNameError, "Last name is required");

        if(!lastName.getText().isEmpty() && lastName.getText().matches("^[a-zA-Z]+$"))
            setError(firstNameError, "Last name should only contain letters");

        if(!middleName.getText().isEmpty() && middleName.getText().matches("^[a-zA-Z]+$"))
            setError(firstNameError, "Middle name should only contain letters");

        if(dateOfBirth.getText().isEmpty())
            setError(dateOfBirthError, "Date of birth field is required");

        if(!dateOfBirth.getText().isEmpty()){
            String dateInput = dateOfBirth.getText();
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate selectedDate = LocalDate.parse(dateInput, formatter);
            if(selectedDate.isAfter(today)){
                setError(dateOfBirthError, "Date of birth should be at least today");
            }
        }

        if(genderComboBox.getSelectedItem() == null)
            setError(sexError, "Gender is required");

        if(contactNumber.getText().isEmpty())
            setError(contactNumberError,"Contact number field is required");

        if(!contactNumber.getText().isEmpty() && contactNumber.getText().matches("^09\\d{9}$"))
            setError(contactNumberError,"Invalid phone number format");

        if(email.getText().isEmpty())
            setError(emailError,"Email field is required");

        if(nationalityComboBox.getSelectedItem() == null)
            setError(nationalityError,"Nationality is required");

        if(completeAddress.getText().isEmpty())
            setError(completeAddressError, "Complete address is required");
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
