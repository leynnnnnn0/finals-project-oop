import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Resident;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {
    public MFXPasswordField password;
    public MFXTextField email;
    public Label loginErrorMessage;
    public Label passwordError;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void login() {
        loginErrorMessage.setText("");
        passwordError.setText("");
        User user = new User();
        Resident resident = new Resident();
        resident.setEmail(email.getText());
        resident.setPassword(password.getText());
        user.setEmail(email.getText());
        user.setPasscode(password.getText());

        if(user.getEmail().isEmpty()){
           loginErrorMessage.setText("Email is required");
        }
        if(user.getPasscode().isEmpty()){
            passwordError.setText("Password is required");
        }
        if(user.getPasscode().isEmpty() || user.getEmail().isEmpty()){
            return;
        }
        System.out.println(resident.getEmail() + " " + resident.getPassword());
        Resident res = resident.auth();

        try{
            if (user.auth()) {
                Main.setUser(user);
                Main.showMainScreen();
            }
            else if(res.getFirst_name() != null){
                Main.setResident(res);
                Main.showResidentScreen();
            }
            else {
                loginErrorMessage.setText("Invalid Login Credentials");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


}
