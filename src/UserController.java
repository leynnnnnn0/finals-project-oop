import helpers.ConfirmationDialogService;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import model.Resident;
import model.User;

public class UserController implements Initializable, ConfirmationDialogService {
    public AnchorPane usersIndexPane;
    private Timer searchTimer;
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
    public TextField searchField;

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

        showConfirmationDialog(
                "Confirm Create",
                "Are you sure you want to create a new user?",
                () -> {
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

                    showSuccessNotification("Success", "New User Created Successfully!");
                }
        );
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
        String middleName = editMiddleName.getText() == null ? "" : editMiddleName.getText();
        editFirstName.setText(selectedUser.getFirstName());
        editMiddleName.setText(middleName);
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
        showConfirmationDialog(
                "Confirm Delete",
                "Are you sure you want to delete this user? This action cannot be undone.",
                () -> {
                    selectedUser.delete(selectedUser.getId());
                    setTableData();
                    usersIndexPane.setVisible(true);
                    userViewPane.setVisible(false);
                    userEditPane.setVisible(false);
                    userCreatePane.setVisible(false);

                    showSuccessNotification("Success", "User Deleted.");
                }
        );
    }

    public void updateUserDetails(ActionEvent actionEvent) {
        if (!validateEditInputs()) {
            return;
        }

        showConfirmationDialog(
                "Confirm Update",
                "Are you sure you want to update this user's details?",
                () -> {
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
                    user.update(selectedUser.getId());
                    setTableData();

                    showSuccessNotification("Success", "User Details Updated Successfully!");
                    usersIndexPane.setVisible(true);
                    userEditPane.setVisible(false);
                }
        );
    }

    private boolean validateEditInputs() {
        clearEditErrors();

        boolean isValid = true;

        if (editFirstName.getText().isEmpty()) {
            setError(editFirstNameError, "First name is required");
            isValid = false;
        } else if (!editFirstName.getText().matches("^[a-zA-Z\\s]{2,50}$")) {
            setError(editFirstNameError, "First name must be 2-50 characters, using only letters");
            isValid = false;
        }

        if (editLastName.getText().isEmpty()) {
            setError(editLastNameError, "Last name is required");
            isValid = false;
        } else if (!editLastName.getText().matches("^[a-zA-Z\\s]{2,50}$")) {
            setError(editLastNameError, "Last name must be 2-50 characters, using only letters");
            isValid = false;
        }

        if (editRolesComboBox.getSelectedItem() == null) {
            setError(editRoleError, "Role is required");
            isValid = false;
        }

        if (editContactNumber.getText().isEmpty()) {
            setError(editPhoneNumberError, "Contact number is required");
            isValid = false;
        } else if (!editContactNumber.getText().matches("^(09|\\+639)\\d{9}$")) {
            setError(editPhoneNumberError, "Invalid phone number. Must start with 09 or +639 and be 10-12 digits");
            isValid = false;
        }

        if (editEmail.getText().isEmpty()) {
            setError(editEmailError, "Email is required");
            isValid = false;
        } else if (!editEmail.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            setError(editEmailError, "Invalid email format");
            isValid = false;
        }

        return isValid;
    }

    private void clearEditErrors() {
        editFirstNameError.setText("");
        editMiddleNameError.setText("");
        editLastNameError.setText("");
        editRoleError.setText("");
        editPhoneNumberError.setText("");
        editEmailError.setText("");
    }

    public void search(KeyEvent keyEvent) {
        try{
            if (searchTimer != null) {
                searchTimer.cancel();
            }

            searchTimer = new Timer();
            searchTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    javafx.application.Platform.runLater(() -> {
                        User userModel = new User();
                        ObservableList<User> allRecords = userModel.getAllRecords(User.class);

                        ObservableList<User> filteredRecords = allRecords.filtered(user ->
                                user.getFirstName().toLowerCase().contains(searchField.getText().toLowerCase()) ||
                                        user.getLastName().toLowerCase().contains(searchField.getText().toLowerCase())
                        );

                        table.setItems(filteredRecords);
                    });
                }
            }, 700);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
