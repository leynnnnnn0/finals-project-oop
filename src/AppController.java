import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppController implements Initializable {
    public MFXButton homeSidebarText;
    public MFXButton residentsSidebarText;
    public MFXButton usersSidebarText;
    public MFXButton barangayCertificateText;
    public MFXButton blottersText;
    @FXML
    private Label exit;

    @FXML
    private StackPane contentArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(e -> {
            System.exit(0);
        });
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("home.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException e) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null, e);
        }

        resetButtonStyles();
        homeSidebarText.setStyle("-fx-text-fill: #2D60FF; -fx-font-weight: bold;");

    }

    private void resetButtonStyles() {
        homeSidebarText.setStyle("-fx-text-fill: #b1b1b1; -fx-font-weight: bold;");
        residentsSidebarText.setStyle("-fx-text-fill: #b1b1b1; -fx-font-weight: bold;");
        usersSidebarText.setStyle("-fx-text-fill: #b1b1b1; -fx-font-weight: bold;");
        barangayCertificateText.setStyle("-fx-text-fill: #b1b1b1; -fx-font-weight: bold;");
        blottersText.setStyle("-fx-text-fill: #b1b1b1; -fx-font-weight: bold;");

    }

    public void home(javafx.event.ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        homeSidebarText.setStyle("-fx-text-fill: #2D60FF; -fx-font-weight: bold;");

        Parent fxml = FXMLLoader.load(getClass().getResource("home.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void residents(javafx.event.ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        residentsSidebarText.setStyle("-fx-text-fill: #2D60FF; -fx-font-weight: bold;");

        Parent fxml = FXMLLoader.load(getClass().getResource("residents.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void users(javafx.event.ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        usersSidebarText.setStyle("-fx-text-fill: #2D60FF; -fx-font-weight: bold;");

        Parent fxml = FXMLLoader.load(getClass().getResource("user.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }


    public void logout(ActionEvent actionEvent) {
        try{
            Main.showLoginScreen();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void barangayCertificate(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        barangayCertificateText.setStyle("-fx-text-fill: #2D60FF; -fx-font-weight: bold;");

        Parent fxml = FXMLLoader.load(getClass().getResource("barangay-certificate.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void blotters(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        blottersText.setStyle("-fx-text-fill: #2D60FF; -fx-font-weight: bold;");

        Parent fxml = FXMLLoader.load(getClass().getResource("blotters.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
}
