import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Resident;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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

}
