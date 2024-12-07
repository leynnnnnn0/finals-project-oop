import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {
    private static double xOffset = 0;
    private static double yOffset = 0;
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showMainScreen() throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("app.fxml")));

        // Use the existing scene's width and height
        Scene existingScene = primaryStage.getScene();
        double width = existingScene != null ? existingScene.getWidth() : 1000;
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

    public static void main(String[] args) {
        launch(args);
    }
}