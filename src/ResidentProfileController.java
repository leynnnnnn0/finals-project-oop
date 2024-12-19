import io.github.palexdev.materialfx.controls.MFXTableView;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Blotter;
import model.Resident;

import java.net.URL;
import java.util.ResourceBundle;

public class ResidentProfileController implements Initializable {

    public Label completeAddress;
    public Label nationality;
    public Label dateOfBirth;
    public Label email;
    public Label gender;
    public Label contactNumber;
    public Label middleName;
    public Label lastName;
    public Label firstName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Resident resident = Main.getResident();
        completeAddress.setText(resident.getComplete_address());
        nationality.setText(resident.getNationality());
        dateOfBirth.setText(String.valueOf(resident.getDate_of_birth()));
        email.setText(resident.getEmail());
        contactNumber.setText(resident.getContact_number());
        middleName.setText(resident.getMiddle_name());
        firstName.setText(resident.getFirst_name());
        lastName.setText(resident.getLast_name());
        gender.setText(resident.getSex());
    }
}
