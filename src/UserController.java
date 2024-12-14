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

import model.Resident;
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
    public AnchorPane userViewPane;
    public AnchorPane userEditPane;
    public Label createUserText;
    public User selectedUser;
    public MFXTextField editFirstName;
    public Label editFirstNameError;
    public MFXTextField editMiddleName;
    public Label editMiddleNameError;
    public MFXTextField editLastName;
    public Label editLastNameError;
    public MFXTextField editContactNumber;
    public Label editPhoneNumberError;
    public MFXTextField editEmail;
    public Label editEmailError;
    public MFXFilterComboBox<String> editRolesComboBox;
    public Label editRoleError;

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
        editRolesComboBox.setItems(rolesOption);
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

        table.getSelectionModel().selectionProperty().addListener((observable, oldValue, newValue) -> {
            User user = table.getSelectionModel().getSelectedValues().getFirst();
            selectedUser = user;
            infolistFullName.setText(user.getFullName());
            infolistPhoneNumber.setText(user.getPhoneNumber());
            infolistEmail.setText(user.getEmail());
            infolistRole.setText(user.getRole());

            userViewPane.setVisible(true);
            usersIndexPane.setVisible(false);
            userCreatePane.setVisible(false);

        });

    }

    private void setTableData()
    {
        User residentModel = new User();
        ObservableList<User> data = residentModel.getAllRecords(User.class);
        table.setItems(data);
    }

    public void backToIndex(ActionEvent actionEvent) {
        usersIndexPane.setVisible(true);
        userViewPane.setVisible(false);
        userEditPane.setVisible(false);
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
                is_admin,
                "password"
        );
        user.create();
        setTableData();
        usersIndexPane.setVisible(true);
        userCreatePane.setVisible(false);
    }

    private boolean validateInputs() {
        clearErrors();

        boolean isValid = true;

        if (firstName.getText().isEmpty()) {
            setError(firstNameError, "First name is required");
            isValid = false;
        } else if (!firstName.getText().matches("^[a-zA-Z\\s]{2,50}$")) {
            setError(firstNameError, "First name must be 2-50 characters, using only letters");
            isValid = false;
        }

        if (!middleName.getText().isEmpty()) {
            if (!middleName.getText().matches("^[a-zA-Z\\s]{1,50}$")) {
                setError(middleNameError, "Middle name must be 1-50 characters, using only letters");
                isValid = false;
            }
        }

        if (lastName.getText().isEmpty()) {
            setError(lastNameError, "Last name is required");
            isValid = false;
        } else if (!lastName.getText().matches("^[a-zA-Z\\s]{2,50}$")) {
            setError(lastNameError, "Last name must be 2-50 characters, using only letters");
            isValid = false;
        }

        if (rolesComboBox.getSelectedItem() == null) {
            setError(roleError, "Role is required");
            isValid = false;
        }

        if (contactNumber.getText().isEmpty()) {
            setError(phoneNumberError, "Contact number is required");
            isValid = false;
        } else if (!contactNumber.getText().matches("^(09|\\+639)\\d{9}$")) {
            setError(phoneNumberError, "Invalid phone number. Must start with 09 or +639 and be 10-12 digits");
            isValid = false;
        }

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

    public void editUserDetails(ActionEvent actionEvent) {
        editFirstName.setText(selectedUser.getFirstName());
        editMiddleName.setText(selectedUser.getMiddleName());
        editLastName.setText(selectedUser.getLastName());
        editContactNumber.setText(selectedUser.getPhoneNumber());
        editEmail.setText(selectedUser.getEmail());
        editRolesComboBox.selectItem(selectedUser.getRole());

        usersIndexPane.setVisible(false);
        userViewPane.setVisible(false);
        userCreatePane.setVisible(false);
        userEditPane.setVisible(true);

    }

    public void deleteUser(ActionEvent actionEvent) {
        selectedUser.delete(selectedUser.getId());
        usersIndexPane.setVisible(true);
        userViewPane.setVisible(false);
        userEditPane.setVisible(false);
        userCreatePane.setVisible(false);
    }

    public void updateUserDetails(ActionEvent actionEvent) {
        System.out.println(editRolesComboBox.getSelectedItem());
        boolean is_admin = editRolesComboBox.getSelectedItem().equals("Admin");
        User user = new User(
                editFirstName.getText(),
                editMiddleName.getText(),
                editLastName.getText(),
                editContactNumber.getText(),
                editEmail.getText(),
                is_admin,
                selectedUser.getPasscode()
        );
        user.update(selectedUser.getId() + "");
        setTableData();
        usersIndexPane.setVisible(true);
        userEditPane.setVisible(false);
    }
}
