import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
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
        boolean is_admin = rolesComboBox.getSelectedItem().equals("Admin");
        User user = new User(firstName.getText(), middleName.getText(), lastName.getText(), contactNumber.getText(), email.getText(), is_admin);
        user.create();
        setTableData();
        usersIndexPane.setVisible(true);
        userCreatePane.setVisible(false);


    }
}
