import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
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

public class ResidentLayoutController implements Initializable {
    public Label exit;
    public MFXButton dashboardSidebarText;
    public StackPane contentArea;
    public MFXButton profileSidebarText;
    public MFXButton blotterSidebarText;
    public MFXButton barangayCertificateSidebarText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(e -> {
            System.exit(0);
        });

        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("resident-dashboard.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException e) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null, e);
        }

        resetButtonStyles();
        dashboardSidebarText.setStyle("-fx-text-fill: #5e24c3; -fx-font-weight: bold;");
    }

    public void logout(ActionEvent actionEvent) {
        try{
            Main.showLoginScreen();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void resetButtonStyles() {
        dashboardSidebarText.setStyle("-fx-text-fill: #b1b1b1; -fx-font-weight: bold;");
        profileSidebarText.setStyle("-fx-text-fill: #b1b1b1; -fx-font-weight: bold;");
        blotterSidebarText.setStyle("-fx-text-fill: #b1b1b1; -fx-font-weight: bold;");
        barangayCertificateSidebarText.setStyle("-fx-text-fill: #b1b1b1; -fx-font-weight: bold;");
    }

    public void dashboard(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        dashboardSidebarText.setStyle("-fx-text-fill: #5e24c3; -fx-font-weight: bold;");

        Parent fxml = FXMLLoader.load(getClass().getResource("resident-dashboard.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }





    public void profile(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        profileSidebarText.setStyle("-fx-text-fill: #5e24c3; -fx-font-weight: bold;");

        Parent fxml = FXMLLoader.load(getClass().getResource("resident-profile.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void blotter(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        blotterSidebarText.setStyle("-fx-text-fill: #5e24c3; -fx-font-weight: bold;");

        Parent fxml = FXMLLoader.load(getClass().getResource("resident-blotters.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void barangayCertificate(ActionEvent actionEvent) throws IOException {
        resetButtonStyles();
        barangayCertificateSidebarText.setStyle("-fx-text-fill: #5e24c3; -fx-font-weight: bold;");

        Parent fxml = FXMLLoader.load(getClass().getResource("resident-barangay-certificate.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
}
