import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import model.User;

public class UserController implements Initializable {
    public AnchorPane usersIndexPane;
    public MFXTableView<User> table;
    public AnchorPane userCreatePane;
    public MFXFilterComboBox<String> rolesComboBox;
    public MFXTextField email;
    public MFXTextField contactNumber;
    public MFXTextField middleName;
    public MFXTextField firstName;
    public MFXTextField lastName;
    public Label roleError;
    public Label emailError;
    public Label phoneNumberError;
    public Label lastNameError;
    public Label middleNameError;
    public Label firstNameError;
    public Label infolistRole;
    public Label infolistPasscode;
    public Label infolistEmail;
    public Label infolistPhoneNumber;
    public Label infolistFullName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        setTableData();
        table.autosizeColumnsOnInitialization();

        ObservableList<String> rolesOption = FXCollections.observableArrayList();
        rolesOption.addAll(
                "Admin",
                "Sub Admin"
        );
        rolesComboBox.setItems(rolesOption);
    }

    public void openCreateNewUserPage(ActionEvent actionEvent) {
        usersIndexPane.setVisible(false);
        userCreatePane.setVisible(true);
    }



    private void setupTable() {
        MFXTableColumn<User> fullNameColumn = new MFXTableColumn<>("Full Name");
        MFXTableColumn<User> phoneNumberColumn = new MFXTableColumn<>("Contact Number");
        MFXTableColumn<User> emailColumn = new MFXTableColumn<>("Email");
        MFXTableColumn<User> roleColumn = new MFXTableColumn<>("Role");

        fullNameColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(User::getFullName));
        phoneNumberColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(User::getPhoneNumber));
        emailColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(User::getEmail));
        roleColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(User::getRole));

        table.getTableColumns().addAll(fullNameColumn, phoneNumberColumn, emailColumn, roleColumn);

    }

    private void setTableData()
    {
        User residentModel = new User();
        ObservableList<User> data = residentModel.getAllRecords(User.class);
        table.setItems(data);
    }

    public void backToIndex(ActionEvent actionEvent) {
        usersIndexPane.setVisible(true);
        userCreatePane.setVisible(false);
    }

    public void createNewUser(ActionEvent actionEvent) {
        if (!validateInputs()) {
            return;
        }

        boolean is_admin = rolesComboBox.getSelectedItem().equals("Admin");
        User user = new User(
                firstName.getText(),
                middleName.getText(),
                lastName.getText(),
                contactNumber.getText(),
                email.getText(),
                is_admin
        );
        user.create();
        setTableData();
        usersIndexPane.setVisible(true);
        userCreatePane.setVisible(false);
    }

    private boolean validateInputs() {
        // Clear previous error messages
        clearErrors();

        boolean isValid = true;

        // First Name Validation
        if (firstName.getText().isEmpty()) {
            setError(firstNameError, "First name is required");
            isValid = false;
        } else if (!firstName.getText().matches("^[a-zA-Z\\s]{2,50}$")) {
            setError(firstNameError, "First name must be 2-50 characters, using only letters");
            isValid = false;
        }

        // Middle Name Validation (optional)
        if (!middleName.getText().isEmpty()) {
            if (!middleName.getText().matches("^[a-zA-Z\\s]{1,50}$")) {
                setError(middleNameError, "Middle name must be 1-50 characters, using only letters");
                isValid = false;
            }
        }

        // Last Name Validation
        if (lastName.getText().isEmpty()) {
            setError(lastNameError, "Last name is required");
            isValid = false;
        } else if (!lastName.getText().matches("^[a-zA-Z\\s]{2,50}$")) {
            setError(lastNameError, "Last name must be 2-50 characters, using only letters");
            isValid = false;
        }

        // Role Validation
        if (rolesComboBox.getSelectedItem() == null) {
            setError(roleError, "Role is required");
            isValid = false;
        }

        // Contact Number Validation
        if (contactNumber.getText().isEmpty()) {
            setError(phoneNumberError, "Contact number is required");
            isValid = false;
        } else if (!contactNumber.getText().matches("^(09|\\+639)\\d{9}$")) {
            setError(phoneNumberError, "Invalid phone number. Must start with 09 or +639 and be 10-12 digits");
            isValid = false;
        }

        // Email Validation
        if (email.getText().isEmpty()) {
            setError(emailError, "Email is required");
            isValid = false;
        } else if (!email.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            setError(emailError, "Invalid email format");
            isValid = false;
        }

        return isValid;
    }

    private void clearErrors() {
        firstNameError.setText("");
        middleNameError.setText("");
        lastNameError.setText("");
        roleError.setText("");
        phoneNumberError.setText("");
        emailError.setText("");
    }

    private void setError(Label label, String message) {
        label.setText(message);
    }
}
