import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Resident;
import model.User;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static double xOffset = 0;
    private static double yOffset = 0;
    private static Stage primaryStage;
    private static Resident resident;
    private static User user;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("app.fxml")));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root, 1050, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showResidentScreen() throws Exception {
        changeScene("resident-layout.fxml");
    }

    public static void showSignupScreen() throws Exception {
        changeScene("signup.fxml");
    }

    public static void showMainScreen() throws Exception {
        changeScene("app.fxml");
    }

    public static void showLoginScreen() throws Exception {
        changeScene("login.fxml");
    }

    public static void changeScene(String name) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(name)));

        Scene existingScene = primaryStage.getScene();
        double width = existingScene != null ? existingScene.getWidth() : 1050;
        double height = existingScene != null ? existingScene.getHeight() : 600;

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static User getUser() {
        return user;
    }

    public static void setResident(Resident resident) {
        Main.resident = resident;
    }

    public static void setUser(User user) {
        Main.user = user;
    }

    public static Resident getResident() {
        return resident;
    }

    public static void main(String[] args) {
        launch(args);
    }
}