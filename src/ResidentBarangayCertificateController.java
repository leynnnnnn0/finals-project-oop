import helpers.ConfirmationDialogService;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.BarangayCertificate;
import model.Blotter;
import model.Resident;

import java.net.URL;
import java.util.ResourceBundle;

public class ResidentBarangayCertificateController implements Initializable, ConfirmationDialogService {
    public AnchorPane certificateIndexPane;
    public MFXTableView<BarangayCertificate> table;
    public TextField searchField;
    public TextArea additionalCertification;
    public Label additionalCertificationError;
    public AnchorPane createCertificatePane;
    public MFXTextField reasonForRequest;
    public Label reasonForRequestError;
    public AnchorPane certificateViewPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        setTableData();
        table.autosizeColumnsOnInitialization();
    }

    private void setTableData()
    {
        BarangayCertificate barangayCertificate = new BarangayCertificate();
        ObservableList<BarangayCertificate> data = barangayCertificate.getAllRecords(BarangayCertificate.class, " WHERE full_name = '" + Main.getResident().getFullName() + "'");
        table.setItems(data);
    }

    private void setupTable() {
        MFXTableColumn<BarangayCertificate> id = new MFXTableColumn<>("Id");
        MFXTableColumn<BarangayCertificate> fullName = new MFXTableColumn<>("Full Name");
        MFXTableColumn<BarangayCertificate> completeAddress = new MFXTableColumn<>("Complete Address");
        MFXTableColumn<BarangayCertificate> reasonForRequest = new MFXTableColumn<>("Reason For Request");
        MFXTableColumn<BarangayCertificate> status = new MFXTableColumn<>("Status");

        id.setRowCellFactory(_ -> new MFXTableRowCell<>(BarangayCertificate::getId));
        fullName.setRowCellFactory(_ -> new MFXTableRowCell<>(BarangayCertificate::getFullName));
        completeAddress.setRowCellFactory(_ -> new MFXTableRowCell<>(BarangayCertificate::getCompleteAddress));
        reasonForRequest.setRowCellFactory(_ -> new MFXTableRowCell<>(BarangayCertificate::getReasonForRequest));
        status.setRowCellFactory(_ -> new MFXTableRowCell<>(BarangayCertificate::getStatus));

        table.getTableColumns().addAll(id, fullName, completeAddress, reasonForRequest, status);


        table.getSelectionModel().selectionProperty().addListener((observable, oldValue, newValue) -> {
            BarangayCertificate barangayCertificate = table.getSelectionModel().getSelectedValues().getFirst();



        });
    }


    public void openCreateBarangayCertificateRequest(ActionEvent actionEvent) {
        createCertificatePane.setVisible(true);
        certificateIndexPane.setVisible(false);
    }

    public void search(KeyEvent keyEvent) {

    }

    private boolean validateForm() {
        boolean isValid = true;

        reasonForRequestError.setText("");

        String reason = reasonForRequest.getText().trim();
        if (reason.isEmpty()) {
            reasonForRequestError.setText("Reason for request is required");
            reasonForRequestError.setVisible(true);
            isValid = false;
        }

        return isValid;
    }

    public void createBarangayCertificateRequest(ActionEvent actionEvent) {
        if (!validateForm()) {
            return;
        }
        Resident resident = Main.getResident();
        String additionalCertification = this.additionalCertification.getText().trim().isEmpty() ? null : this.additionalCertification.getText().trim();
        showConfirmationDialog(
                "Create New Request",
                "Are you sure you want to create this request?",
                () -> {
                    Resident res = Main.getResident();
                    BarangayCertificate barangayCertificate = new BarangayCertificate(
                            resident.getFullName(),
                            resident.getComplete_address(),
                            additionalCertification,
                            reasonForRequest.getText().trim(),
                            "pending"
                    );
                    barangayCertificate.create();

                    clearForm();

                    createCertificatePane.setVisible(false);
                    certificateIndexPane.setVisible(true);
                    setTableData();
                });
    }

    private void clearForm() {
        additionalCertification.clear();
        reasonForRequest.clear();

        reasonForRequestError.setText("");
        additionalCertificationError.setText("");
    }

    public void backToIndex(ActionEvent actionEvent) {
        createCertificatePane.setVisible(false);
        certificateIndexPane.setVisible(true);
    }


}
