import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void openCreateNewUserPage(ActionEvent actionEvent) {
        setupTable();




        table.autosizeColumnsOnInitialization();
    }

    private void setupTable() {
        MFXTableColumn<User> fullNameColumn = new MFXTableColumn<>("Full Name");
        MFXTableColumn<User> phoneNumberColumn = new MFXTableColumn<>("Contact Number");
        MFXTableColumn<User> emailColumn = new MFXTableColumn<>("Email");



//        fullNameColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(User::getFullName));
//        phoneNumberColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(User::getContact_number));
//        emailColumn.setRowCellFactory(_ -> new MFXTableRowCell<>(User::getEmail));

        table.getTableColumns().addAll(fullNameColumn, phoneNumberColumn, emailColumn);



    }
}
