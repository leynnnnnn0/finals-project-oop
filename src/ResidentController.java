import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Resident;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResidentController implements Initializable {
    public Resident resident;
    public AnchorPane residentIndexPane;
    public AnchorPane residentCreatePane;
    public MFXComboBox<String> genderComboBox;
    public MFXFilterComboBox<String> nationalityComboBox;

    public TextField firstName;
    public MFXTableView<Resident> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        Resident residentModel = new Resident();
        ObservableList<Resident> residentData = residentModel.getAllRecords(Resident.class);
        table.setItems(residentData);


        table.autosizeColumnsOnInitialization();
    }

    public void openCreateNewResidentPage(ActionEvent actionEvent) throws IOException {
        residentIndexPane.setVisible(false);
        residentCreatePane.setVisible(true);
    }

    public void createNewResident(ActionEvent actionEvent) {
        System.out.println("First Name field: " + firstName);
        System.out.println("Is First Name field null? " + (firstName == null));
        if (firstName != null) {
            System.out.println("First Name text: " + firstName.getText());
        }

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

}
