import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ResidentController {
    public Resident resident;
    public AnchorPane residentIndexPane;
    public AnchorPane residentCreatePane;
    public MFXComboBox<String> genderComboBox;
    public MFXFilterComboBox<String> nationalityComboBox;

    public TextField firstName;
    public void initialize() {
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
}
