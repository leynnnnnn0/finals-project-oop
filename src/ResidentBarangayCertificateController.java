import helpers.ConfirmationDialogService;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.BarangayCertificate;

import java.net.URL;
import java.util.ResourceBundle;

public class ResidentBarangayCertificateController implements Initializable, ConfirmationDialogService {
    public AnchorPane certificateIndexPane;
    public MFXTableView<BarangayCertificate> table;
    public TextField searchField;
    public MFXTextField fullName;
    public Label fullNameError;
    public TextArea completeAddress;
    public Label completeAddressError;
    public TextArea additionalCertification;
    public Label additionalCertificationError;
    public AnchorPane createCertificatePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void openCreateBarangayCertificateRequest(ActionEvent actionEvent) {
        createCertificatePane.setVisible(true);
        certificateIndexPane.setVisible(false);
    }

    public void search(KeyEvent keyEvent) {

    }

    public void createBarangayCertificateRequest(ActionEvent actionEvent) {
    }

    public void backToIndex(ActionEvent actionEvent) {
        createCertificatePane.setVisible(false);
        certificateIndexPane.setVisible(true);
    }
}
