import database.Database;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.Initializable;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {
    public MFXPasswordField password;
    public MFXTextField email;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public boolean login()
    {
        User user = new User();
        user.setEmail(email.getText());
        user.setPasscode(password.getText());
        System.out.println(user.auth());;
        return true;
    }


}
