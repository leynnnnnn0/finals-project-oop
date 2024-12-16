package helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public interface ConfirmationDialogService {
    default void showConfirmationDialog(String title, String content, Runnable onConfirm) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle(title);
        confirmDialog.setHeaderText(null);
        confirmDialog.setContentText(content);

        confirmDialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                onConfirm.run();
            }
        });
    }

    default void showConfirmationDialog(String content, Runnable onConfirm) {
        showConfirmationDialog("Confirm Action", content, onConfirm);
    }

}
