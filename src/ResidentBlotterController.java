import helpers.ConfirmationDialogService;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Blotter;

import java.net.URL;
import java.util.ResourceBundle;

public class ResidentBlotterController implements Initializable, ConfirmationDialogService {
    public AnchorPane blottersIndexPane;
    public TextField searchField;
    public AnchorPane blotterCreatePane;
    public MFXTextField referenceNumber;
    public Label referenceNumberError;
    public MFXTextField complainantReporter;
    public Label complainantReporterError;
    public MFXTextField against;
    public Label againstError;
    public Label reportedDateError;
    public MFXDatePicker reportedDate;
    public TextArea details;
    public Label detailsError;
    public MFXTableView<Blotter> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void search(KeyEvent keyEvent) {
    }

    public void openCreateBlotterRequest(ActionEvent actionEvent) {
        blotterCreatePane.setVisible(true);
        blottersIndexPane.setVisible(false);
    }

    public void backToIndex(ActionEvent actionEvent) {
        blotterCreatePane.setVisible(false);
        blottersIndexPane.setVisible(true);
    }

    public void createBlotterRequest(ActionEvent actionEvent) {

    }
}
